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
package rx.subscriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import rx.Subscription;
import rx.util.CompositeException;

/**
 * Subscription that represents a group of Subscriptions that are unsubscribed
 * together.
 * 
 * @see <a href="http://msdn.microsoft.com/en-us/library/system.reactive.disposables.compositedisposable(v=vs.103).aspx">Rx.Net equivalent CompositeDisposable</a>
 */
public final class CompositeSubscription implements Subscription {

    private final AtomicReference<State> state = new AtomicReference<State>();

    private static final class State {
        final boolean isUnsubscribed;
        final List<Subscription> subscriptions;

        State(boolean u, List<Subscription> s) {
            this.isUnsubscribed = u;
            this.subscriptions = s;
        }

        State unsubscribe() {
            return new State(true, subscriptions);
        }

        State add(Subscription s) {
            List<Subscription> newSubscriptions = new ArrayList<Subscription>();
            newSubscriptions.addAll(subscriptions);
            newSubscriptions.add(s);
            return new State(isUnsubscribed, newSubscriptions);
        }

        State remove(Subscription s) {
            List<Subscription> newSubscriptions = new ArrayList<Subscription>();
            newSubscriptions.addAll(subscriptions);
            newSubscriptions.remove(s); // only first occurrence
            return new State(isUnsubscribed, newSubscriptions);
        }

        State clear() {
            return new State(isUnsubscribed, new ArrayList<Subscription>());
        }
    }

    public CompositeSubscription(final Subscription... subscriptions) {
        state.set(new State(false, Arrays.asList(subscriptions)));
    }

    public boolean isUnsubscribed() {
        return state.get().isUnsubscribed;
    }

    public void add(final Subscription s) {
        State oldState;
        State newState;
        do {
            oldState = state.get();
            if (oldState.isUnsubscribed) {
                s.unsubscribe();
                return;
            } else {
                newState = oldState.add(s);
            }
        } while (!state.compareAndSet(oldState, newState));
    }

    public void remove(final Subscription s) {
        State oldState;
        State newState;
        do {
            oldState = state.get();
            if (oldState.isUnsubscribed) {
                return;
            } else {
                newState = oldState.remove(s);
            }
        } while (!state.compareAndSet(oldState, newState));
        // if we removed successfully we then need to call unsubscribe on it
        s.unsubscribe();
    }

    public void clear() {
        State oldState;
        State newState;
        do {
            oldState = state.get();
            if (oldState.isUnsubscribed) {
                return;
            } else {
                newState = oldState.clear();
            }
        } while (!state.compareAndSet(oldState, newState));
        // if we cleared successfully we then need to call unsubscribe on all previous
        unsubscribeFromAll(oldState.subscriptions);
    }

    @Override
    public void unsubscribe() {
        State oldState;
        State newState;
        do {
            oldState = state.get();
            if (oldState.isUnsubscribed) {
                return;
            } else {
                newState = oldState.unsubscribe();
            }
        } while (!state.compareAndSet(oldState, newState));
        unsubscribeFromAll(oldState.subscriptions);
    }

    private static void unsubscribeFromAll(Collection<Subscription> subscriptions) {
        final Collection<Throwable> es = new ArrayList<Throwable>();
        for (Subscription s : subscriptions) {
            try {
                s.unsubscribe();
            } catch (Throwable e) {
                es.add(e);
            }
        }
        if (!es.isEmpty()) {
            throw new CompositeException(
                    "Failed to unsubscribe to 1 or more subscriptions.", es);
        }
    }
}
