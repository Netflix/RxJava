/**
 * Copyright 2013 Netflix, Inc.
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
package rx.concurrency;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscription;
import rx.util.functions.Func0;

public class SleepingAction implements Func0<Subscription> {
    private final Func0<Subscription> underlying;
    private final Scheduler scheduler;
    private final long execTime;

    public SleepingAction(Func0<Subscription> underlying, Scheduler scheduler, long timespan, TimeUnit timeUnit) {
        this.underlying = underlying;
        this.scheduler = scheduler;
        this.execTime = scheduler.now() + timeUnit.toMillis(timespan);
    }

    @Override
    public Subscription call() {
        if (execTime < scheduler.now()) {
            try {
                // this will block the current thread ... which doesn't seem to work well with CurrentThreadScheduler
                // shouldn't CurrentThreadScheduler be capable of doing other things while this is sleeping?
                // In fact, this will block any of the concurrent systems -- it will take up a thread in a threadpool and make it sleep
                // whereas I would think it should schedule itself on a timer
                Thread.sleep(scheduler.now() - execTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        return underlying.call();
    }
}
