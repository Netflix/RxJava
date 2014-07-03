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
package rx;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import rx.Observable.OnSubscribe;
import rx.Observable.Operator;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

public class SubscriberTest {

    /**
     * Should request n for whatever the final Subscriber asks for
     */
    @Test
    public void testRequestFromFinalSubscribeWithRequestValue() {
        Subscriber<String> s = new TestSubscriber<String>();
        s.request(10);
        final AtomicInteger r = new AtomicInteger();
        s.setProducer(new Producer() {

            @Override
            public void request(int n) {
                r.set(n);
            }

        });
        assertEquals(10, r.get());
    }

    /**
     * Should request -1 for infinite
     */
    @Test
    public void testRequestFromFinalSubscribeWithoutRequestValue() {
        Subscriber<String> s = new TestSubscriber<String>();
        final AtomicInteger r = new AtomicInteger();
        s.setProducer(new Producer() {

            @Override
            public void request(int n) {
                r.set(n);
            }

        });
        assertEquals(-1, r.get());
    }

    @Test
    public void testRequestFromChainedOperator() {
        Subscriber<String> s = new TestSubscriber<String>();
        Operator<String, String> o = new Operator<String, String>() {

            @Override
            public Subscriber<? super String> call(Subscriber<? super String> s) {
                return new Subscriber<String>(s) {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String t) {

                    }

                };
            }

        };
        s.request(10);
        Subscriber<? super String> ns = o.call(s);

        final AtomicInteger r = new AtomicInteger();
        // set set the producer at the top of the chain (ns) and it should flow through the operator to the (s) subscriber
        // and then it should request up with the value set on the final Subscriber (s)
        ns.setProducer(new Producer() {

            @Override
            public void request(int n) {
                r.set(n);
            }

        });
        assertEquals(10, r.get());
    }

    @Test
    public void testRequestFromDecoupledOperator() {
        Subscriber<String> s = new TestSubscriber<String>();
        Operator<String, String> o = new Operator<String, String>() {

            @Override
            public Subscriber<? super String> call(Subscriber<? super String> s) {
                return new Subscriber<String>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String t) {

                    }

                };
            }

        };
        s.request(10);
        Subscriber<? super String> ns = o.call(s);

        final AtomicInteger r = new AtomicInteger();
        // set set the producer at the top of the chain (ns) and it should flow through the operator to the (s) subscriber
        // and then it should request up with the value set on the final Subscriber (s)
        ns.setProducer(new Producer() {

            @Override
            public void request(int n) {
                r.set(n);
            }

        });
        // this will be -1 because it is decoupled and nothing requsted on the Operator subscriber
        assertEquals(-1, r.get());
    }

    @Test
    public void testRequestFromDecoupledOperatorThatRequestsN() {
        Subscriber<String> s = new TestSubscriber<String>();
        final AtomicInteger innerR = new AtomicInteger();
        Operator<String, String> o = new Operator<String, String>() {

            @Override
            public Subscriber<? super String> call(Subscriber<? super String> child) {
                // we want to decouple the chain so set our own Producer on the child instead of it coming from the parent
                child.setProducer(new Producer() {

                    @Override
                    public void request(int n) {
                        innerR.set(n);
                    }

                });

                Subscriber<String> as = new Subscriber<String>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String t) {

                    }

                };
                // we request 99 up to the parent
                as.request(99);
                return as;
            }

        };
        s.request(10);
        Subscriber<? super String> ns = o.call(s);

        final AtomicInteger r = new AtomicInteger();
        // set set the producer at the top of the chain (ns) and it should flow through the operator to the (s) subscriber
        // and then it should request up with the value set on the final Subscriber (s)
        ns.setProducer(new Producer() {

            @Override
            public void request(int n) {
                r.set(n);
            }

        });
        assertEquals(99, r.get());
        assertEquals(10, innerR.get());
    }

    @Test
    public void testRequestToObservable() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ts.request(3);
        final AtomicInteger requested = new AtomicInteger();
        Observable.create(new OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> s) {
                s.setProducer(new Producer() {

                    @Override
                    public void request(int n) {
                        requested.set(n);
                    }

                });
            }

        }).subscribe(ts);
        assertEquals(3, requested.get());
    }

    @Test
    public void testRequestThroughMap() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ts.request(3);
        final AtomicInteger requested = new AtomicInteger();
        Observable.create(new OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> s) {
                s.setProducer(new Producer() {

                    @Override
                    public void request(int n) {
                        requested.set(n);
                    }

                });
            }

        }).map(new Func1<Integer, Integer>() {

            @Override
            public Integer call(Integer t1) {
                return t1;
            }

        }).subscribe(ts);
        assertEquals(3, requested.get());
    }

    @Test
    public void testRequestThroughTakeThatReducesRequest() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ts.request(3);
        final AtomicInteger requested = new AtomicInteger();
        Observable.create(new OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> s) {
                s.setProducer(new Producer() {

                    @Override
                    public void request(int n) {
                        requested.set(n);
                    }

                });
            }

        }).take(2).subscribe(ts);
        assertEquals(2, requested.get());
    }

    @Test
    public void testRequestThroughTakeWhereRequestIsSmallerThanTake() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ts.request(3);
        final AtomicInteger requested = new AtomicInteger();
        Observable.create(new OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> s) {
                s.setProducer(new Producer() {

                    @Override
                    public void request(int n) {
                        requested.set(n);
                    }

                });
            }

        }).take(10).subscribe(ts);
        assertEquals(3, requested.get());
    }

    @Test
    public void testSetProducerFromOperator() {
        final AtomicInteger requested1 = new AtomicInteger();
        final AtomicInteger requested2 = new AtomicInteger();
        final AtomicReference<Producer> producer1 = new AtomicReference<Producer>();
        final AtomicReference<Producer> producer2 = new AtomicReference<Producer>();
        final AtomicReference<Producer> gotProducer = new AtomicReference<Producer>();
        Observable.create(new OnSubscribe<Integer>() {

            @Override
            public void call(final Subscriber<? super Integer> s) {
                Producer p1 = new Producer() {
                    int index = 0;

                    @Override
                    public void request(int n) {
                        requested1.set(n);
                        System.out.println("onSubscribe => requested: " + n);
                        for (int i = 0; i < n; i++) {
                            s.onNext(index++);
                        }
                    }

                };
                producer1.set(p1);
                s.setProducer(p1);
            }

        }).lift(new Operator<Integer, Integer>() {

            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super Integer> child) {

                Producer p2 = new Producer() {

                    @Override
                    public void request(int n) {
                        System.out.println("lift => requested: " + n);
                        requested2.set(n);
                    }

                };
                producer2.set(p2);
                child.setProducer(p2);

                // we request "1" and this decouples the Producer chain while retaining the Subscription chain
                return new Subscriber<Integer>(child, 5) {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer t) {
                    }

                };
            }

        }).subscribe(new Subscriber<Integer>(1) {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer t) {
                System.out.println(t);
                request(1);
            }

            @Override
            protected Producer onSetProducer(Producer producer) {
                gotProducer.set(producer);
                return producer;
            }

        });

        if (gotProducer.get() != producer2.get()) {
            throw new IllegalStateException("Expecting the producer from lift");
        }
        assertEquals(requested1.get(), 5);
        assertEquals(requested2.get(), 1);

    }
}
