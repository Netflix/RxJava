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
package rx.joins;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.IObservable;
import rx.Notification;
import rx.Observable;
import rx.operators.SafeObservableSubscription;
import rx.util.functions.Action1;

/**
 * Default implementation of a join observer.
 */
public final class JoinObserver1<T> extends ObserverBase<Notification<T>> implements JoinObserver {
    private Object gate;
    private final IObservable<T> source;
    private final Action1<Throwable> onError;
    private final List<ActivePlan0> activePlans;
    private final Queue<Notification<T>> queue;
    private final SafeObservableSubscription subscription;
    private volatile boolean done;
    private final AtomicBoolean subscribed = new AtomicBoolean(false);
    
    public JoinObserver1(IObservable<T> source, Action1<Throwable> onError) {
        this.source = source;
        this.onError = onError;
        queue = new LinkedList<Notification<T>>();
        subscription = new SafeObservableSubscription();
        activePlans = new ArrayList<ActivePlan0>();
    }
    public Queue<Notification<T>> queue() {
        return queue;
    }
    public void addActivePlan(ActivePlan0 activePlan) {
        activePlans.add(activePlan);
    }
    @Override
    public void subscribe(Object gate) {
        if (subscribed.compareAndSet(false, true)) {
            this.gate = gate;
            subscription.wrap(Observable.from(source).materialize().subscribe(this));
        } else {
            throw new IllegalStateException("Can only be subscribed to once.");
        }
    }

    @Override
    public void dequeue() {
        queue.remove();
    }

    @Override
    protected void onNextCore(Notification<T> args) {
        synchronized (gate) {
            if (!done) {
                if (args.isOnError()) {
                    onError.call(args.getThrowable());
                    return;
                }
                queue.add(args);
                
                // remark: activePlans might change while iterating
                for (ActivePlan0 a : new ArrayList<ActivePlan0>(activePlans)) {
                    a.match();
                }
            }
        }
    }

    @Override
    protected void onErrorCore(Throwable e) {
        // not expected
    }

    @Override
    protected void onCompletedCore() {
        // not expected or ignored
    }
    
    
    void removeActivePlan(ActivePlan0 activePlan) {
        activePlans.remove(activePlan);
        if (activePlans.isEmpty()) {
            unsubscribe();
        }
    }

    @Override
    public void unsubscribe() {
        if (!done) {
            done = true;
            subscription.unsubscribe();
        }
    }
    
}
