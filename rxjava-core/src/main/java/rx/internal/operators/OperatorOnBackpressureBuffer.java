/**
 * Copyright 2014 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;

public class OperatorOnBackpressureBuffer<T> implements Operator<T, T> {

    private final NotificationLite<T> on = NotificationLite.instance();

    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> child) {
        // TODO get a different queue implementation
        // TODO start with size hint
        final ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<Object>();
        final AtomicInteger wip = new AtomicInteger();
        final AtomicInteger requested = new AtomicInteger();

        child.setProducer(new Producer() {

            @Override
            public void request(int n) {
                if (requested.getAndAdd(n) == 0) {
                    pollQueue(wip, requested, queue, child);
                }
            }

        });
        return new Subscriber<T>(child) {

            @Override
            public void onCompleted() {
                queue.offer(on.completed());
                pollQueue(wip, requested, queue, child);
            }

            @Override
            public void onError(Throwable e) {
                queue.offer(on.error(e));
                pollQueue(wip, requested, queue, child);
            }

            @Override
            public void onNext(T t) {
                queue.offer(on.next(t));
                pollQueue(wip, requested, queue, child);
            }

        };
    }

    private void pollQueue(AtomicInteger wip, AtomicInteger requested, Queue<Object> queue, Subscriber<? super T> child) {
        // TODO can we do this without putting everything in the queue first so we can fast-path the case when we don't need to queue?
        if (requested.get() > 0) {
            // only one draining at a time
            try {
                /*
                 * This needs to protect against concurrent execution because `request` and `on*` events can come concurrently.
                 */
                if (wip.getAndIncrement() == 0) {
                    while (true) {
                        if (requested.getAndDecrement() > 0) {
                            Object o = queue.poll();
                            if (o == null) {
                                // nothing in queue
                                requested.incrementAndGet();
                                return;
                            }
                            on.accept(child, o);
                        } else {
                            // we hit the end ... so increment back to 0 again
                            requested.incrementAndGet();
                        }
                    }
                }

            } finally {
                wip.decrementAndGet();
            }
        }
    }
}
