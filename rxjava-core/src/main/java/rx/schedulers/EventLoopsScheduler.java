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
package rx.schedulers;

import rx.subscriptions.SubscriptionQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.NewThreadScheduler.NewThreadWorker.ScheduledAction;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/* package */class EventLoopsScheduler extends Scheduler {
    /** Manages a fixed number of workers. */
    static final class FixedSchedulerPool {
        final int cores;
        final ThreadFactory factory = new ThreadFactory() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "RxComputationThreadPool-" + counter.incrementAndGet());
                t.setDaemon(true);
                return t;
            }
        };

        final PoolWorker[] eventLoops;
        long n;

        FixedSchedulerPool() {
            // initialize event loops
            this.cores = Runtime.getRuntime().availableProcessors();
            this.eventLoops = new PoolWorker[cores];
            for (int i = 0; i < cores; i++) {
                this.eventLoops[i] = new PoolWorker(factory);
            }
        }

        public PoolWorker getEventLoop() {
            // simple round robin, improvements to come
            return eventLoops[(int)(n++ % cores)];
        }
    }

    final FixedSchedulerPool pool;
    
    /**
     * Create a scheduler with pool size equal to the available processor
     * count and using least-recent worker selection policy.
     */
    EventLoopsScheduler() {
        pool = new FixedSchedulerPool();
    }
    
    @Override
    public Worker createWorker() {
        return new EventLoopWorker(pool.getEventLoop());
    }

    private static class EventLoopWorker extends Scheduler.Worker {
        /** Undelayed tasks are completed mostly sequentially. */
        private final SubscriptionQueue directTasks = new SubscriptionQueue();
        /** Delayed tasks are completed randomly. */
        private final CompositeSubscription delayedTasks = new CompositeSubscription();
        /** Capture exceptions if both are unsubscribed. */
        private final CompositeSubscription both = new CompositeSubscription(directTasks, delayedTasks);
        private final PoolWorker poolWorker;

        EventLoopWorker(PoolWorker poolWorker) {
            this.poolWorker = poolWorker;
            
        }

        @Override
        public void unsubscribe() {
            both.unsubscribe();
        }

        @Override
        public boolean isUnsubscribed() {
            return both.isUnsubscribed();
        }

        @Override
        public Subscription schedule(Action0 action) {
            if (isUnsubscribed()) {
                // don't schedule, we are unsubscribed
                return Subscriptions.empty();
            }
            
            ScheduledAction s = poolWorker.scheduleActual(action, 0, null);
            directTasks.add(s);
            s.add(directTasks.createDequeuer(s));
            return s;
        }
        @Override
        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            if (delayTime <= 0) {
                return schedule(action);
            }
            if (isUnsubscribed()) {
                // don't schedule, we are unsubscribed
                return Subscriptions.empty();
            }
            
            ScheduledAction s = poolWorker.scheduleActual(action, delayTime, unit);
            delayedTasks.add(s);
            s.addParent(delayedTasks);
            return s;
        }
    }
    
    private static final class PoolWorker extends NewThreadScheduler.NewThreadWorker {
        PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }
}
