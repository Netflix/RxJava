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
package rx.operators;

import java.util.ArrayList;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Ignore;

import org.junit.Test;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Scheduler;
import rx.Scheduler.Inner;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.GroupedObservable;
import rx.observers.TestObserver;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.MultipleAssignmentSubscription;
import rx.subscriptions.Subscriptions;
import rx.util.Timestamped;
import rx.util.functions.Action0;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

public class OperatorSubscribeOnTest {
    
    private class ThreadSubscription implements Subscription {
        private volatile Thread thread;
        
        private final CountDownLatch latch = new CountDownLatch(1);
        
        private final Subscription s = Subscriptions.create(new Action0() {
            
            @Override
            public void call() {
                thread = Thread.currentThread();
                latch.countDown();
            }
            
        });
        
        @Override
        public void unsubscribe() {
            s.unsubscribe();
        }
        
        @Override
        public boolean isUnsubscribed() {
            return s.isUnsubscribed();
        }
        
        public Thread getThread() throws InterruptedException {
            latch.await();
            return thread;
        }
    }
    
    @Test
    public void testSubscribeOnAndVerifySubscribeAndUnsubscribeThreads()
            throws InterruptedException {
        final ThreadSubscription subscription = new ThreadSubscription();
        final AtomicReference<Thread> subscribeThread = new AtomicReference<Thread>();
        Observable<Integer> w = Observable.create(new OnSubscribe<Integer>() {
            
            @Override
            public void call(Subscriber<? super Integer> t1) {
                subscribeThread.set(Thread.currentThread());
                t1.add(subscription);
                t1.onNext(1);
                t1.onNext(2);
                t1.onCompleted();
            }
        });
        
        TestObserver<Integer> observer = new TestObserver<Integer>();
        w.subscribeOn(Schedulers.newThread()).subscribe(observer);
        
        Thread unsubscribeThread = subscription.getThread();
        
        assertNotNull(unsubscribeThread);
        assertNotSame(Thread.currentThread(), unsubscribeThread);
        
        assertNotNull(subscribeThread.get());
        assertNotSame(Thread.currentThread(), subscribeThread.get());
        // True for Schedulers.newThread()
        assertTrue(unsubscribeThread == subscribeThread.get());
        
        observer.assertReceivedOnNext(Arrays.asList(1, 2));
        observer.assertTerminalEvent();
    }
    
    @Test
    public void testIssue813() throws InterruptedException {
        // https://github.com/Netflix/RxJava/issues/813
        final CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatch doneLatch = new CountDownLatch(1);
        
        TestObserver<Integer> observer = new TestObserver<Integer>();
        final ThreadSubscription s = new ThreadSubscription();
        
        final Subscription subscription = Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(
                            final Subscriber<? super Integer> subscriber) {
                        subscriber.add(s);
                        try {
                            latch.await();
                            // Already called "unsubscribe", "isUnsubscribed"
                            // shouble be true
                            if (!subscriber.isUnsubscribed()) {
                                throw new IllegalStateException(
                                        "subscriber.isUnsubscribed should be true");
                            }
                            subscriber.onCompleted();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } catch (Throwable e) {
                            subscriber.onError(e);
                        } finally {
                            doneLatch.countDown();
                        }
                    }
                }).subscribeOn(Schedulers.computation()).subscribe(observer);
        
        subscription.unsubscribe();
        // As unsubscribe is called in other thread, we need to wait for it.
        s.getThread();
        latch.countDown();
        doneLatch.await();
        assertEquals(0, observer.getOnErrorEvents().size());
        assertEquals(1, observer.getOnCompletedEvents().size());
    }
    
    public static class SlowScheduler extends Scheduler {
        final Scheduler actual;
        final long delay;
        final TimeUnit unit;
        public SlowScheduler() {
            this(Schedulers.computation(), 2, TimeUnit.SECONDS);
        }
        public SlowScheduler(Scheduler actual, long delay, TimeUnit unit) {
            this.actual = actual;
            this.delay = delay;
            this.unit = unit;
        }

        @Override
        public Subscription schedule(final Action1<Scheduler.Inner> action) {
            return actual.schedule(action, delay, unit);
        }

        @Override
        public Subscription schedule(final Action1<Scheduler.Inner> action, final long delayTime, final TimeUnit delayUnit) {
            TimeUnit common = delayUnit.compareTo(unit) < 0 ? delayUnit : unit;
            long t = common.convert(delayTime, delayUnit) + common.convert(delay, unit);
            return actual.schedule(action, t, common);
        }
    }
    
    @Test
    public void testSubscribeOnPublishSubjectWithSlowScheduler() {
        PublishSubject<Integer> ps = PublishSubject.create();
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ps.subscribeOn(new SlowScheduler()).subscribe(ts);
        ps.onNext(1);
        ps.onNext(2);
        ps.onCompleted();
        
        ts.awaitTerminalEvent();
        ts.assertReceivedOnNext(Arrays.asList(1, 2));
    }
    
    @Test
    public void testGroupsWithNestedSubscribeOn() throws InterruptedException {
        final ArrayList<String> results = new ArrayList<String>();
        Observable.create(new OnSubscribe<Integer>() {
            
            @Override
            public void call(Subscriber<? super Integer> sub) {
                sub.onNext(1);
                sub.onNext(2);
                sub.onNext(1);
                sub.onNext(2);
                sub.onCompleted();
            }
            
        }).groupBy(new Func1<Integer, Integer>() {
            
            @Override
            public Integer call(Integer t) {
                return t;
            }
            
        }).flatMap(new Func1<GroupedObservable<Integer, Integer>, Observable<String>>() {
            
            @Override
            public Observable<String> call(final GroupedObservable<Integer, Integer> group) {
                return group.subscribeOn(Schedulers.newThread()).map(new Func1<Integer, String>() {
                    
                    @Override
                    public String call(Integer t1) {
                        System.out.println("Received: " + t1 + " on group : " + group.getKey());
                        return "first groups: " + t1;
                    }
                    
                });
            }
            
        }).toBlockingObservable().forEach(new Action1<String>() {
            
            @Override
            public void call(String s) {
                results.add(s);
            }
            
        });
        
        System.out.println("Results: " + results);
        assertEquals(4, results.size());
    }
    
    @Test
    public void testFirstGroupsCompleteAndParentSlowToThenEmitFinalGroupsWhichThenSubscribesOnAndDelaysAndThenCompletes() throws InterruptedException {
        final CountDownLatch first = new CountDownLatch(2); // there are two groups to first complete
        final ArrayList<String> results = new ArrayList<String>();
        Observable.create(new OnSubscribe<Integer>() {
            
            @Override
            public void call(Subscriber<? super Integer> sub) {
                sub.onNext(1);
                sub.onNext(2);
                sub.onNext(1);
                sub.onNext(2);
                try {
                    first.await();
                } catch (InterruptedException e) {
                    sub.onError(e);
                    return;
                }
                sub.onNext(3);
                sub.onNext(3);
                sub.onCompleted();
            }
            
        }).groupBy(new Func1<Integer, Integer>() {
            
            @Override
            public Integer call(Integer t) {
                return t;
            }
            
        }).flatMap(new Func1<GroupedObservable<Integer, Integer>, Observable<String>>() {
            
            @Override
            public Observable<String> call(final GroupedObservable<Integer, Integer> group) {
                if (group.getKey() < 3) {
                    return group.map(new Func1<Integer, String>() {
                        
                        @Override
                        public String call(Integer t1) {
                            return "first groups: " + t1;
                        }
                        
                    })
                            // must take(2) so an onCompleted + unsubscribe happens on these first 2 groups
                            .take(2).doOnCompleted(new Action0() {
                                
                                @Override
                                public void call() {
                                    first.countDown();
                                }
                                
                            });
                } else {
                    return group.subscribeOn(Schedulers.newThread()).delay(400, TimeUnit.MILLISECONDS).map(new Func1<Integer, String>() {
                        
                        @Override
                        public String call(Integer t1) {
                            return "last group: " + t1;
                        }
                        
                    });
                }
            }
            
        }).toBlockingObservable().forEach(new Action1<String>() {
            
            @Override
            public void call(String s) {
                results.add(s);
            }
            
        });
        
        System.out.println("Results: " + results);
        assertEquals(6, results.size());
    }
    void testBoundedBufferingWithSize(int size) throws Exception {
        Observable<Long> timer = Observable.timer(100, 100, TimeUnit.MILLISECONDS);

        final List<Long> deltas = Collections.synchronizedList(new ArrayList<Long>());
        
        Subscription s = timer.timestamp().subscribeOn(
                new SlowScheduler(Schedulers.computation(), 1, TimeUnit.SECONDS), size).map(new Func1<Timestamped<Long>, Long>() {
            @Override
            public Long call(Timestamped<Long> t1) {
                long v = System.currentTimeMillis() - t1.getTimestampMillis();
                return v;
            }
        }).doOnNext(new Action1<Long>() {
            @Override
            public void call(Long t1) {
                deltas.add(t1);
            }
        }).subscribe();
        
        Thread.sleep(2050);
        
        s.unsubscribe();
        
        if (deltas.size() < size + 1) {
            fail("To few items in deltas: " + deltas);
        }
        for (int i = 0; i < size + 1; i++) {
            if (deltas.get(i) < 500) {
                fail(i + "th item arrived too early: " + deltas);
            }
        }
        for (int i = size + 1; i < deltas.size(); i++) {
            if (deltas.get(i) >= 500) {
                fail(i + "th item arrived too late: " + deltas);
            }
        }
    }
    @Test(timeout = 5000)
    public void testBoundedBufferingOfZero() throws Exception {
        testBoundedBufferingWithSize(0);
    }
    @Test(timeout = 5000)
    public void testBoundedBufferingOfOne() throws Exception {
        testBoundedBufferingWithSize(1);
    }
    @Test(timeout = 5000)
    public void testBoundedBufferingOfTwo() throws Exception {
        testBoundedBufferingWithSize(2);
    }
}
