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

package io.reactivex.internal.operators.completable;

import java.util.concurrent.Callable;

import io.reactivex.*;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableErrorSupplier extends Completable {

    final Callable<? extends Throwable> errorSupplier;
    
    public CompletableErrorSupplier(Callable<? extends Throwable> errorSupplier) {
        this.errorSupplier = errorSupplier;
    }

    @Override
    protected void subscribeActual(CompletableSubscriber s) {
        s.onSubscribe(EmptyDisposable.INSTANCE);
        Throwable error;
        
        try {
            error = errorSupplier.call();
        } catch (Throwable e) {
            error = e;
        }
        
        if (error == null) {
            error = new NullPointerException("The error supplied is null");
        }
        s.onError(error);
    }

}
