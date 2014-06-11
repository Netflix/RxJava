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
package rx.usecases;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.logic.BlackHole;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;

/**
 * Exposes an Observable and Observer that increments n Integers and consumes them in a Blackhole.
 */
@State(Scope.Thread)
public class UseCaseInput {
    @Param({ "1", "1024" })
    public int size;

    public Iterable<Integer> iterable;
    public Observable<Integer> observable;
    public Observer<Integer> observer;

    private CountDownLatch latch;

    @Setup
    public void setup(final BlackHole bh) {
        observable = Observable.range(0, size);
        
        iterable = new Iterable<Integer>() {

            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    int i=0;
                    
                    @Override
                    public boolean hasNext() {
                        return i < size;
                    }

                    @Override
                    public Integer next() {
                        return i++;
                    }
                    
                    @Override
                    public void remove() {
                        
                    }
                    
                };
            }
            
        };

        latch = new CountDownLatch(1);

        observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(Integer value) {
                bh.consume(value);
            }
        };

    }

    public void awaitCompletion() throws InterruptedException {
        latch.await();
    }
}
