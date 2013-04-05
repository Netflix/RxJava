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

import rx.Subscription;
import rx.util.functions.Func0;

/**
 * Schedules work on a new thread.
 */
public class NewThreadScheduler extends AbstractScheduler {
    private static final NewThreadScheduler INSTANCE = new NewThreadScheduler();

    public static NewThreadScheduler getInstance() {
        return INSTANCE;
    }

    @Override
    public Subscription schedule(Func0<Subscription> action) {
        final DiscardableAction discardableAction = new DiscardableAction(action);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                discardableAction.call();
            }
        }, "RxNewThreadScheduler");

        t.start();

        return discardableAction;
    }

    @Override
    public Subscription schedule(Func0<Subscription> action, long dueTime, TimeUnit unit) {
        return schedule(new SleepingAction(action, this, dueTime, unit));
    }

}
