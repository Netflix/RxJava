/**
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx.android.schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.functions.Action1;
import android.os.Handler;
import android.os.Looper;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class HandlerThreadSchedulerTest {

    @Test
    public void shouldScheduleImmediateActionOnHandlerThread() {
        final Handler handler = mock(Handler.class);
        final Looper looper = mock(Looper.class);
        when(handler.getLooper()).thenReturn(looper);
        when(looper.getThread()).thenReturn(Thread.currentThread());
        @SuppressWarnings("unchecked")
        final Action0 action = mock(Action0.class);

        Scheduler scheduler = new HandlerThreadScheduler(handler, true);
        Worker inner = scheduler.createWorker();
        inner.schedule(action);

        // verify that we call action directly
        verify(handler).getLooper();
        verify(looper).getThread();

        // verify that the given handler delegates to our action
        verify(action).call();
    }

    @Test
    public void shouldScheduleDelayedActionOnHandlerThread() {
        final Handler handler = mock(Handler.class);
        @SuppressWarnings("unchecked")
        final Action0 action = mock(Action0.class);

        Scheduler scheduler = new HandlerThreadScheduler(handler);
        Worker inner = scheduler.createWorker();
        inner.schedule(action, 1L, TimeUnit.SECONDS);

        // verify that we post to the given Handler
        ArgumentCaptor<Runnable> runnable = ArgumentCaptor.forClass(Runnable.class);
        verify(handler).postDelayed(runnable.capture(), eq(1000L));

        // verify that the given handler delegates to our action
        runnable.getValue().run();
        verify(action).call();
    }
}
