/**
 * Copyright 2016 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import io.reactivex.flowables.BlockingFlowable;
import io.reactivex.observables.BlockingObservable;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class BlockingPerf {
    @Param({ "1", "1000", "1000000" })
    public int times;
    
    Flowable<Integer> flowable;
    
    BlockingFlowable<Integer> flowableBlocking;
    
    Observable<Integer> observable;

    BlockingObservable<Integer> observableBlocking;

    @Setup
    public void setup() {
        Integer[] array = new Integer[times];
        Arrays.fill(array, 777);
        
        flowable = Flowable.fromArray(array);
        
        flowableBlocking = flowable.toBlocking();
        
        observable = Observable.fromArray(array);
        
        observableBlocking = observable.toBlocking();
    }
    
    @Benchmark
    public Object flowableFirst() {
        return flowable.toBlocking().first();
    }

    @Benchmark
    public Object flowableLast() {
        return flowable.toBlocking().last();
    }

    @Benchmark
    public Object flowableBlockingFirst() {
        return flowableBlocking.first();
    }

    @Benchmark
    public Object flowableBlockingLast() {
        return flowableBlocking.last();
    }
    
    @Benchmark
    public Object observableFirst() {
        return observable.toBlocking().first();
    }

    @Benchmark
    public Object observableLast() {
        return observable.toBlocking().last();
    }

    @Benchmark
    public Object observableBlockingFirst() {
        return observableBlocking.first();
    }

    @Benchmark
    public Object observableBlockingLast() {
        return observableBlocking.last();
    }
}