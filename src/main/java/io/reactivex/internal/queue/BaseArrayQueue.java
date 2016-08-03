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

/*
 * The code was inspired by the similarly named JCTools class: 
 * https://github.com/JCTools/JCTools/blob/master/jctools-core/src/main/java/org/jctools/queues/atomic
 */

package io.reactivex.internal.queue;

import java.util.concurrent.atomic.AtomicReferenceArray;

import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.util.Pow2;

abstract class BaseArrayQueue<E> extends AtomicReferenceArray<E> implements SimpleQueue<E> {
    /** */
    private static final long serialVersionUID = 5238363267841964068L;
    protected final int mask;
    public BaseArrayQueue(int capacity) {
        super(Pow2.roundToPowerOfTwo(capacity));
        this.mask = length() - 1;
    }
    
    @Override
    public abstract E poll(); // hide throws Exception, unnecessary for this type of queue
    
    @Override
    public void clear() {
        // we have to test isEmpty because of the weaker poll() guarantee
        while (poll() != null || !isEmpty()) ; // NOPMD
    }
    protected final int calcElementOffset(long index, int mask) {
        return (int)index & mask;
    }
    protected final int calcElementOffset(long index) {
        return (int)index & mask;
    }
    protected final E lvElement(AtomicReferenceArray<E> buffer, int offset) {
        return buffer.get(offset);
    }
    protected final E lpElement(AtomicReferenceArray<E> buffer, int offset) {
        return buffer.get(offset); // no weaker form available
    }
    protected final E lpElement(int offset) {
        return get(offset); // no weaker form available
    }
    protected final void spElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.lazySet(offset, value);  // no weaker form available
    }
    protected final void spElement(int offset, E value) {
        lazySet(offset, value);  // no weaker form available
    }
    protected final void soElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.lazySet(offset, value);
    }
    protected final void soElement(int offset, E value) {
        lazySet(offset, value);
    }
    protected final void svElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.set(offset, value);
    }
    protected final E lvElement(int offset) {
        return get(offset);
    }
}

