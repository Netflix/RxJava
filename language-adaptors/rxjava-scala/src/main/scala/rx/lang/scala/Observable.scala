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


package rx.lang.scala

/**
 * The Observable interface that implements the Reactive Pattern.
 */
class Observable[+T](val asJava: rx.Observable[_ <: T])
  // Uncommenting this line combined with `new Observable(...)` instead of `new Observable[T](...)`
  // makes the compiler crash
  extends AnyVal 
{
  import scala.collection.JavaConverters._
  import scala.collection.Seq
  import scala.concurrent.duration.{Duration, TimeUnit}
  import rx.{Observable => JObservable}
  import rx.util.functions._
  import rx.lang.scala.{Notification, Subscription, Scheduler, Observer}
  import rx.lang.scala.util._
  import rx.lang.scala.subjects.Subject
  import rx.lang.scala.observables.BlockingObservable
  import rx.lang.scala.ImplicitFunctionConversions._

  /**
   * An {@link Observer} must call an Observable's {@code subscribe} method in order to
   * receive items and notifications from the Observable.
   * 
   * <p>A typical implementation of {@code subscribe} does the following:
   * <p>
   * It stores a reference to the Observer in a collection object, such as a {@code List<T>} object.
   * <p>
   * It returns a reference to the {@link Subscription} interface. This enables Observers to
   * unsubscribe, that is, to stop receiving items and notifications before the Observable stops
   * sending them, which also invokes the Observer's {@link Observer#onCompleted onCompleted} method.
   * <p>
   * An <code>Observable&lt;T&gt;</code> instance is responsible for accepting all subscriptions
   * and notifying all Observers. Unless the documentation for a particular
   * <code>Observable&lt;T&gt;</code> implementation indicates otherwise, Observers should make no
   * assumptions about the order in which multiple Observers will receive their notifications.
   * <p>
   * 
   * @param observer
   *            the observer
   * @return a {@link Subscription} reference with which the {@link Observer} can stop receiving items
   *         before the Observable has finished sending them
   * @throws IllegalArgumentException
   *             if the {@link Observer} provided as the argument to {@code subscribe()} is {@code null}
   */
  def subscribe(observer: Observer[T]): Subscription = {
    asJava.subscribe(observer)
  }
  
  /**
   * An {@link Observer} must call an Observable's {@code subscribe} method in order to
   * receive items and notifications from the Observable.
   * 
   * <p>A typical implementation of {@code subscribe} does the following:
   * <p>
   * It stores a reference to the Observer in a collection object, such as a {@code List<T>} object.
   * <p>
   * It returns a reference to the {@link Subscription} interface. This enables Observers to
   * unsubscribe, that is, to stop receiving items and notifications before the Observable stops
   * sending them, which also invokes the Observer's {@link Observer#onCompleted onCompleted} method.
   * <p>
   * An {@code Observable<T>} instance is responsible for accepting all subscriptions
   * and notifying all Observers. Unless the documentation for a particular {@code Observable<T>} implementation indicates otherwise, Observers should make no
   * assumptions about the order in which multiple Observers will receive their notifications.
   * <p>
   * 
   * @param observer
   *            the observer
   * @param scheduler
   *            the {@link Scheduler} on which Observers subscribe to the Observable
   * @return a {@link Subscription} reference with which Observers can stop receiving items and
   *         notifications before the Observable has finished sending them
   * @throws IllegalArgumentException
   *             if an argument to {@code subscribe()} is {@code null}
   */
  def subscribe(observer: Observer[T], scheduler: Scheduler): Subscription = {
    asJava.subscribe(observer, scheduler)
  }
  
  def subscribe(onNext: T => Unit): Subscription = {
    asJava.subscribe(onNext)
  }
  
  def subscribe(onNext: T => Unit, onError: Throwable => Unit): Subscription = {
    asJava.subscribe(onNext, onError)
  }
  
  def subscribe(onNext: T => Unit, onError: Throwable => Unit, onComplete: () => Unit): Subscription = {
    asJava.subscribe(onNext, onError, onComplete)
  }
  
  def subscribe(onNext: T => Unit, onError: Throwable => Unit, onComplete: () => Unit, scheduler: Scheduler): Subscription = {
    asJava.subscribe(onNext, onError, onComplete, scheduler)
  }
  
  def subscribe(onNext: T => Unit, onError: Throwable => Unit, scheduler: Scheduler): Subscription = {
    asJava.subscribe(onNext, onError, scheduler)
  }
  
  def subscribe(onNext: T => Unit, scheduler: Scheduler): Subscription = {
    asJava.subscribe(onNext, scheduler)
  }

  /**
   * Returns a {@link ConnectableObservable} that upon connection causes the source Observable to
   * push results into the specified subject.
   * 
   * @param subject
   *            the {@link Subject} for the {@link ConnectableObservable} to push source items
   *            into
   * @param <R>
   *            result type
   * @return a pair of a start function and an {@link Observable} such that when the start function
   *         is called, the Observable starts to push results into the specified {@link Subject}
   */
  def multicast[R](subject: Subject[T, R]): (() => Subscription, Observable[R]) = {
    val javaCO = asJava.multicast[R](subject)
    (() => javaCO.connect(), Observable[R](javaCO))
  }
  
  /**
   * Returns an Observable that first emits the items emitted by this, and then the items emitted
   * by that.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/concat.png">
   * 
   * @param that
   *            an Observable to be appended
   * @return an Observable that emits items that are the result of combining the items emitted by
   *         this and that, one after the other
   */
  def ++[U >: T](that: Observable[U]): Observable[U] = {
    val o1: JObservable[_ <: U] = this.asJava
    val o2: JObservable[_ <: U] = that.asJava
    Observable(JObservable.concat(o1, o2))
  }

  /**
   * Returns an Observable that emits the items emitted by two or more Observables, one after the
   * other.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/concat.png">
   *
   * @return an Observable that emits items that are the result of combining the items emitted by
   *         the source Observables, one after the other
   */
  def concat[U](implicit evidence: Observable[T] <:< Observable[Observable[U]]): Observable[U] = {
    val o2: Observable[Observable[U]] = this
    val o3: Observable[rx.Observable[_ <: U]] = o2.map(_.asJava)
    val o4: rx.Observable[_ <: rx.Observable[_ <: U]] = o3.asJava
    val o5 = rx.Observable.concat[U](o4)
    Observable[U](o5)
  }

  /**
   * Wraps this Observable in another Observable that ensures that the resulting
   * Observable is chronologically well-behaved.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/synchronize.png">
   * <p>
   * A well-behaved Observable does not interleave its invocations of the {@link Observer#onNext onNext}, {@link Observer#onCompleted onCompleted}, and {@link Observer#onError onError} methods of
   * its {@link Observer}s; it invokes {@code onCompleted} or {@code onError} only once; and it never invokes {@code onNext} after invoking either {@code onCompleted} or {@code onError}.
   * {@code synchronize} enforces this, and the Observable it returns invokes {@code onNext} and {@code onCompleted} or {@code onError} synchronously.
   * 
   * @param observable
   *            the source Observable
   * @param <T>
   *            the type of item emitted by the source Observable
   * @return an Observable that is a chronologically well-behaved version of the source
   *         Observable, and that synchronously notifies its {@link Observer}s
   */
  def synchronize: Observable[T] = {
    Observable[T](asJava.synchronize)
  }
  
  /**
   * Wraps each item emitted by a source Observable in a {@link Timestamped} object.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/timestamp.png">
   * 
   * @return an Observable that emits timestamped items from the source Observable
   */
  def timestamp: Observable[Timestamped[T]] = {
    Observable[Timestamped[T]](asJava.timestamp())
  }
  
  /**
   * Returns an Observable formed from this Observable and another Observable by combining 
   * corresponding elements in pairs. 
   * The number of {@code onNext} invocations of the resulting {@code Observable[(T, U)]}
   * is the minumum of the number of {@code onNext} invocations of {@code this} and {@code that}. 
   */
  def zip[U](that: Observable[U]): Observable[(T, U)] = {
    Observable[(T, U)](JObservable.zip[T, U, (T, U)](this.asJava, that.asJava, (t: T, u: U) => (t, u)))
  }
    
  // public static <R> Observable<R> zip(Observable<? extends Observable<?>> ws, final FuncN<? extends R> zipFunction) {
  
  /**
   * Zips this Observable with its indices.
   * 
   * @return An Observable emitting pairs consisting of all elements of this Observable paired with 
   *         their index. Indices start at 0.
   */
  def zipWithIndex: Observable[(T, Int)] = {
    val fScala: (T, Integer) => (T, Int) = (elem: T, index: Integer) => (elem, index)
    val fJava : Func2[_ >: T, Integer, _ <: (T, Int)] = fScala
    Observable[(T, Int)](asJava.mapWithIndex[(T, Int)](fJava))
  }
    
  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces connected non-overlapping buffers. The current buffer is
   * emitted and replaced with a new buffer when the Observable produced by the specified {@link Func0} produces a {@link rx.util.Closing} object. The * {@link Func0} will then
   * be used to create a new Observable to listen for the end of the next buffer.
   * 
   * @param bufferClosingSelector
   *            The {@link Func0} which is used to produce an {@link Observable} for every buffer created.
   *            When this {@link Observable} produces a {@link rx.util.Closing} object, the associated buffer
   *            is emitted and replaced with a new one.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers, which are emitted
   *         when the current {@link Observable} created with the {@link Func0} argument produces a {@link rx.util.Closing} object.
   */
  def buffer(bufferClosingSelector: () => Observable[Closing]) : Observable[Seq[T]] = {
    val f: Func0[_ <: rx.Observable[_ <: Closing]] = bufferClosingSelector().asJava
    val jObs: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(f)
    Observable.jObsOfListToScObsOfSeq(jObs.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces buffers. Buffers are created when the specified "bufferOpenings"
   * Observable produces a {@link rx.util.Opening} object. Additionally the {@link Func0} argument
   * is used to create an Observable which produces {@link rx.util.Closing} objects. When this
   * Observable produces such an object, the associated buffer is emitted.
   * 
   * @param bufferOpenings
   *            The {@link Observable} which, when it produces a {@link rx.util.Opening} object, will cause
   *            another buffer to be created.
   * @param bufferClosingSelector
   *            The {@link Func0} which is used to produce an {@link Observable} for every buffer created.
   *            When this {@link Observable} produces a {@link rx.util.Closing} object, the associated buffer
   *            is emitted.
   * @return
   *         An {@link Observable} which produces buffers which are created and emitted when the specified {@link Observable}s publish certain objects.
   */
  def buffer(bufferOpenings: Observable[Opening], bufferClosingSelector: Opening => Observable[Closing]): Observable[Seq[T]] = {
    val opening: rx.Observable[_ <: Opening] = bufferOpenings.asJava 
    val closing: Func1[Opening, _ <: rx.Observable[_ <: Closing]] = (o: Opening) => bufferClosingSelector(o).asJava
    val jObs: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(opening, closing)
    Observable.jObsOfListToScObsOfSeq(jObs.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces connected non-overlapping buffers, each containing "count"
   * elements. When the source Observable completes or encounters an error, the current
   * buffer is emitted, and the event is propagated.
   * 
   * @param count
   *            The maximum size of each buffer before it should be emitted.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers containing at most
   *         "count" produced values.
   */
  def buffer(count: Int): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(count)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces buffers every "skip" values, each containing "count"
   * elements. When the source Observable completes or encounters an error, the current
   * buffer is emitted, and the event is propagated.
   * 
   * @param count
   *            The maximum size of each buffer before it should be emitted.
   * @param skip
   *            How many produced values need to be skipped before starting a new buffer. Note that when "skip" and
   *            "count" are equals that this is the same operation as {@link Observable#buffer(int)}.
   * @return
   *         An {@link Observable} which produces buffers every "skipped" values containing at most
   *         "count" produced values.
   */
  def buffer(count: Int, skip: Int): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(count, skip)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }
  
  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces connected non-overlapping buffers, each of a fixed duration
   * specified by the "timespan" argument. When the source Observable completes or encounters
   * an error, the current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted, and
   *            replaced with a new buffer.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers with a fixed duration.
   */
  def buffer(timespan: Duration): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(timespan.length, timespan.unit)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values.
   * 
   * <p>This Observable produces connected non-overlapping buffers, each of a fixed duration
   * specified by the "timespan" argument. When the source Observable completes or encounters
   * an error, the current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted, and
   *            replaced with a new buffer.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a buffer.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers with a fixed duration.
   */
  def buffer(timespan: Duration, scheduler: Scheduler): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(timespan.length, timespan.unit, scheduler)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  } 

  /**
   * Creates an Observable which produces buffers of collected values. This Observable produces connected
   * non-overlapping buffers, each of a fixed duration specified by the "timespan" argument or a maximum size
   * specified by the "count" argument (which ever is reached first). When the source Observable completes
   * or encounters an error, the current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted, and
   *            replaced with a new buffer.
   * @param count
   *            The maximum size of each buffer before it should be emitted.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers which are emitted after
   *         a fixed duration or when the buffer has reached maximum capacity (which ever occurs first).
   */
  def buffer(timespan: Duration, count: Int): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(timespan.length, timespan.unit, count)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values. This Observable produces connected
   * non-overlapping buffers, each of a fixed duration specified by the "timespan" argument or a maximum size
   * specified by the "count" argument (which ever is reached first). When the source Observable completes
   * or encounters an error, the current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted, and
   *            replaced with a new buffer.
   * @param count
   *            The maximum size of each buffer before it should be emitted.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a buffer.
   * @return
   *         An {@link Observable} which produces connected non-overlapping buffers which are emitted after
   *         a fixed duration or when the buffer has reached maximum capacity (which ever occurs first).
   */
  def buffer(timespan: Duration, count: Int, scheduler: Scheduler): Observable[Seq[T]] = {
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(timespan.length, timespan.unit, count, scheduler)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  }

  /**
   * Creates an Observable which produces buffers of collected values. This Observable starts a new buffer
   * periodically, which is determined by the "timeshift" argument. Each buffer is emitted after a fixed timespan
   * specified by the "timespan" argument. When the source Observable completes or encounters an error, the
   * current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted.
   * @param timeshift
   *            The period of time after which a new buffer will be created.
   * @return
   *         An {@link Observable} which produces new buffers periodically, and these are emitted after
   *         a fixed timespan has elapsed.
   */
  def buffer(timespan: Duration, timeshift: Duration): Observable[Seq[T]] = {
    val span: Long = timespan.length
    val shift: Long = timespan.unit.convert(timeshift.length, timeshift.unit)
    val unit: TimeUnit = timespan.unit
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(span, shift, unit)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  } 
  
  /**
   * Creates an Observable which produces buffers of collected values. This Observable starts a new buffer
   * periodically, which is determined by the "timeshift" argument. Each buffer is emitted after a fixed timespan
   * specified by the "timespan" argument. When the source Observable completes or encounters an error, the
   * current buffer is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each buffer is collecting values before it should be emitted.
   * @param timeshift
   *            The period of time after which a new buffer will be created.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a buffer.
   * @return
   *         An {@link Observable} which produces new buffers periodically, and these are emitted after
   *         a fixed timespan has elapsed.
   */
  def buffer(timespan: Duration, timeshift: Duration, scheduler: Scheduler): Observable[Seq[T]] = {
    val span: Long = timespan.length
    val shift: Long = timespan.unit.convert(timeshift.length, timeshift.unit)
    val unit: TimeUnit = timespan.unit
    val oJava: rx.Observable[_ <: java.util.List[_]] = asJava.buffer(span, shift, unit, scheduler)
    Observable.jObsOfListToScObsOfSeq(oJava.asInstanceOf[rx.Observable[_ <: java.util.List[T]]])
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows. The current window is emitted and replaced with a new window when the
   * Observable produced by the specified {@link Func0} produces a {@link rx.util.Closing} object. The {@link Func0} will then be used to create a new Observable to listen for the end of the next
   * window.
   * 
   * @param closingSelector
   *            The {@link Func0} which is used to produce an {@link Observable} for every window created.
   *            When this {@link Observable} produces a {@link rx.util.Closing} object, the associated window
   *            is emitted and replaced with a new one.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows, which are emitted
   *         when the current {@link Observable} created with the {@link Func0} argument produces a {@link rx.util.Closing} object.
   */
  def window(closingSelector: () => Observable[Closing]): Observable[Observable[T]] = {
    val func : Func0[_ <: rx.Observable[_ <: Closing]] = closingSelector().asJava
    val o1: rx.Observable[_ <: rx.Observable[_]] = asJava.window(func)
    val o2 = new Observable[rx.Observable[_]](o1).map((x: rx.Observable[_]) => {
      val x2 = x.asInstanceOf[rx.Observable[_ <: T]]
      Observable[T](x2)
    })
    o2
  }

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces windows.
   * Chunks are created when the specified "windowOpenings" Observable produces a {@link rx.util.Opening} object.
   * Additionally the {@link Func0} argument is used to create an Observable which produces {@link rx.util.Closing} objects. When this Observable produces such an object, the associated window is
   * emitted.
   * 
   * @param windowOpenings
   *            The {@link Observable} which when it produces a {@link rx.util.Opening} object, will cause
   *            another window to be created.
   * @param closingSelector
   *            The {@link Func0} which is used to produce an {@link Observable} for every window created.
   *            When this {@link Observable} produces a {@link rx.util.Closing} object, the associated window
   *            is emitted.
   * @return
   *         An {@link Observable} which produces windows which are created and emitted when the specified {@link Observable}s publish certain objects.
   */
  def window(windowOpenings: Observable[Opening], closingSelector: Opening => Observable[Closing]) = {
    Observable.jObsOfJObsToScObsOfScObs(
        asJava.window(windowOpenings.asJava, (op: Opening) => closingSelector(op).asJava))
             : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows, each containing "count" elements. When the source Observable completes or
   * encounters an error, the current window is emitted, and the event is propagated.
   * 
   * @param count
   *            The maximum size of each window before it should be emitted.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows containing at most
   *         "count" produced values.
   */
  def window(count: Int): Observable[Observable[T]] = {
    // this unnecessary ascription is needed because of this bug (without, compiler crashes):
    // https://issues.scala-lang.org/browse/SI-7818
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(count)) : Observable[Observable[T]]
  }

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces windows every
   * "skip" values, each containing "count" elements. When the source Observable completes or encounters an error,
   * the current window is emitted and the event is propagated.
   * 
   * @param count
   *            The maximum size of each window before it should be emitted.
   * @param skip
   *            How many produced values need to be skipped before starting a new window. Note that when "skip" and
   *            "count" are equals that this is the same operation as {@link Observable#window(Observable, int)}.
   * @return
   *         An {@link Observable} which produces windows every "skipped" values containing at most
   *         "count" produced values.
   */
  def window(count: Int, skip: Int): Observable[Observable[T]] = {
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(count, skip))
        : Observable[Observable[T]] // SI-7818
  }

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows, each of a fixed duration specified by the "timespan" argument. When the source
   * Observable completes or encounters an error, the current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted, and
   *            replaced with a new window.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows with a fixed duration.
   */
  def window(timespan: Duration): Observable[Observable[T]] = {
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(timespan.length, timespan.unit))
        : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows, each of a fixed duration specified by the "timespan" argument. When the source
   * Observable completes or encounters an error, the current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted, and
   *            replaced with a new window.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a window.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows with a fixed duration.
   */
  def window(timespan: Duration, scheduler: Scheduler): Observable[Observable[T]] = {
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(timespan.length, timespan.unit, scheduler))
        : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows, each of a fixed duration specified by the "timespan" argument or a maximum size
   * specified by the "count" argument (which ever is reached first). When the source Observable completes
   * or encounters an error, the current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted, and
   *            replaced with a new window.
   * @param count
   *            The maximum size of each window before it should be emitted.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows which are emitted after
   *         a fixed duration or when the window has reached maximum capacity (which ever occurs first).
   */
  def window(timespan: Duration, count: Int): Observable[Observable[T]] = {
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(timespan.length, timespan.unit, count))
        : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable produces connected
   * non-overlapping windows, each of a fixed duration specified by the "timespan" argument or a maximum size
   * specified by the "count" argument (which ever is reached first). When the source Observable completes
   * or encounters an error, the current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted, and
   *            replaced with a new window.
   * @param count
   *            The maximum size of each window before it should be emitted.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a window.
   * @return
   *         An {@link Observable} which produces connected non-overlapping windows which are emitted after
   *         a fixed duration or when the window has reached maximum capacity (which ever occurs first).
   */
  def window(timespan: Duration, count: Int, scheduler: Scheduler): Observable[Observable[T]] = {
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(timespan.length, timespan.unit, count, scheduler))
        : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable starts a new window
   * periodically, which is determined by the "timeshift" argument. Each window is emitted after a fixed timespan
   * specified by the "timespan" argument. When the source Observable completes or encounters an error, the
   * current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted.
   * @param timeshift
   *            The period of time after which a new window will be created.
   * @return
   *         An {@link Observable} which produces new windows periodically, and these are emitted after
   *         a fixed timespan has elapsed.
   */
  def window(timespan: Duration, timeshift: Duration): Observable[Observable[T]] = {
    val span: Long = timespan.length
    val shift: Long = timespan.unit.convert(timeshift.length, timeshift.unit)
    val unit: TimeUnit = timespan.unit
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(span, shift, unit))
        : Observable[Observable[T]] // SI-7818
  } 

  /**
   * Creates an Observable which produces windows of collected values. This Observable starts a new window
   * periodically, which is determined by the "timeshift" argument. Each window is emitted after a fixed timespan
   * specified by the "timespan" argument. When the source Observable completes or encounters an error, the
   * current window is emitted and the event is propagated.
   * 
   * @param timespan
   *            The period of time each window is collecting values before it should be emitted.
   * @param timeshift
   *            The period of time after which a new window will be created.
   * @param scheduler
   *            The {@link Scheduler} to use when determining the end and start of a window.
   * @return
   *         An {@link Observable} which produces new windows periodically, and these are emitted after
   *         a fixed timespan has elapsed.
   */
  def window(timespan: Duration, timeshift: Duration, scheduler: Scheduler): Observable[Observable[T]] = {
    val span: Long = timespan.length
    val shift: Long = timespan.unit.convert(timeshift.length, timeshift.unit)
    val unit: TimeUnit = timespan.unit
    Observable.jObsOfJObsToScObsOfScObs(asJava.window(span, shift, unit, scheduler))
        : Observable[Observable[T]] // SI-7818
  } 
  
  /**
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/filter.png">
   * 
   * @param predicate
   *            a function that evaluates the items emitted by the source Observable, returning {@code true} if they pass the filter
   * @return an Observable that emits only those items in the original Observable that the filter
   *         evaluates as {@code true}
   */
  def filter(predicate: T => Boolean): Observable[T] = {
    Observable[T](asJava.filter(predicate))
  }

  /**
   * Registers an {@link Action0} to be called when this Observable invokes {@link Observer#onCompleted onCompleted} or {@link Observer#onError onError}.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/finallyDo.png">
   * 
   * @param action
   *            an {@link Action0} to be invoked when the source Observable finishes
   * @return an Observable that emits the same items as the source Observable, then invokes the {@link Action0}
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh212133(v=vs.103).aspx">MSDN: Observable.Finally Method</a>
   */
  def finallyDo(action: () => Unit): Observable[T] = {
    Observable[T](asJava.finallyDo(action))
  } 

  /**
   * Creates a new Observable by applying a function that you supply to each item emitted by
   * the source Observable, where that function returns an Observable, and then merging those
   * resulting Observables and emitting the results of this merger.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/flatMap.png">
   * <p>
   * Note: {@code mapMany} and {@code flatMap} are equivalent.
   * 
   * @param func
   *            a function that, when applied to an item emitted by the source Observable, returns
   *            an Observable
   * @return an Observable that emits the result of applying the transformation function to each
   *         item emitted by the source Observable and merging the results of the Observables
   *         obtained from this transformation.
   * @see #mapMany(Func1)
   */
  def flatMap[R](f: T => Observable[R]): Observable[R] = {
    Observable[R](asJava.flatMap[R]((t: T) => f(t).asJava))
  }
  
  /**
   * Returns an Observable that applies the given function to each item emitted by an
   * Observable and emits the result.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/map.png">
   * 
   * @param func
   *            a function to apply to each item emitted by the Observable
   * @return an Observable that emits the items from the source Observable, transformed by the
   *         given function
   */
  def map[R](func: T => R): Observable[R] = {
    Observable[R](asJava.map[R](func))
  }
  
  /**
   * Turns all of the notifications from a source Observable into {@link Observer#onNext onNext} emissions, and marks them with their original notification types within {@link Notification} objects.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/materialize.png">
   * 
   * @return an Observable whose items are the result of materializing the items and
   *         notifications of the source Observable
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh229453(v=VS.103).aspx">MSDN: Observable.materialize</a>
   */
  def materialize: Observable[Notification[T]] = {
    Observable[Notification[T]](asJava.materialize())
  }

  /**
   * Asynchronously subscribes and unsubscribes Observers on the specified {@link Scheduler}.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/subscribeOn.png">
   * 
   * @param scheduler
   *            the {@link Scheduler} to perform subscription and unsubscription actions on
   * @return the source Observable modified so that its subscriptions and unsubscriptions happen
   *         on the specified {@link Scheduler}
   */
  def subscribeOn(scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.subscribeOn(scheduler))
  } 

  /**
   * Asynchronously notify {@link Observer}s on the specified {@link Scheduler}.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/observeOn.png">
   * 
   * @param scheduler
   *            the {@link Scheduler} to notify {@link Observer}s on
   * @return the source Observable modified so that its {@link Observer}s are notified on the
   *         specified {@link Scheduler}
   */
  def observeOn(scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.observeOn(scheduler))
  }
 
  /**
   * Returns an Observable that reverses the effect of {@link #materialize materialize} by
   * transforming the {@link Notification} objects emitted by the source Observable into the items
   * or notifications they represent.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/dematerialize.png">
   * 
   * @return an Observable that emits the items and notifications embedded in the {@link Notification} objects emitted by the source Observable
   */
  // with =:= it does not work, why?
  def dematerialize[U](implicit evidence: T <:< Notification[U]): Observable[U] = {
    val o = asJava.dematerialize[U]()
    Observable[U](o)
  }
  
  /**
   * Instruct an Observable to pass control to another Observable rather than invoking {@link Observer#onError onError} if it encounters an error.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/onErrorResumeNext.png">
   * <p>
   * By default, when an Observable encounters an error that prevents it from emitting the
   * expected item to its {@link Observer}, the Observable invokes its Observer's
   * <code>onError</code> method, and then quits without invoking any more of its Observer's
   * methods. The <code>onErrorResumeNext</code> method changes this behavior. If you pass a
   * function that returns an Observable (<code>resumeFunction</code>) to
   * <code>onErrorResumeNext</code>, if the original Observable encounters an error, instead of
   * invoking its Observer's <code>onError</code> method, it will instead relinquish control to
   * the Observable returned from <code>resumeFunction</code>, which will invoke the Observer's {@link Observer#onNext onNext} method if it is able to do so. In such a case, because no
   * Observable necessarily invokes <code>onError</code>, the Observer may never know that an
   * error happened.
   * <p>
   * You can use this to prevent errors from propagating or to supply fallback data should errors
   * be encountered.
   * 
   * @param resumeFunction
   *            a function that returns an Observable that will take over if the source Observable
   *            encounters an error
   * @return the original Observable, with appropriately modified behavior
   */
  def onErrorResumeNext[U >: T](resumeFunction: Throwable => Observable[U]): Observable[U] = {
    val f: Func1[Throwable, rx.Observable[_ <: U]] = (t: Throwable) => resumeFunction(t).asJava
    val f2 = f.asInstanceOf[Func1[Throwable, rx.Observable[Nothing]]]
    Observable[U](asJava.onErrorResumeNext(f2))
  }
  
  /**
   * Instruct an Observable to pass control to another Observable rather than invoking {@link Observer#onError onError} if it encounters an error.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/onErrorResumeNext.png">
   * <p>
   * By default, when an Observable encounters an error that prevents it from emitting the
   * expected item to its {@link Observer}, the Observable invokes its Observer's
   * <code>onError</code> method, and then quits without invoking any more of its Observer's
   * methods. The <code>onErrorResumeNext</code> method changes this behavior. If you pass
   * another Observable (<code>resumeSequence</code>) to an Observable's
   * <code>onErrorResumeNext</code> method, if the original Observable encounters an error,
   * instead of invoking its Observer's <code>onError</code> method, it will instead relinquish
   * control to <code>resumeSequence</code> which will invoke the Observer's {@link Observer#onNext onNext} method if it is able to do so. In such a case, because no
   * Observable necessarily invokes <code>onError</code>, the Observer may never know that an
   * error happened.
   * <p>
   * You can use this to prevent errors from propagating or to supply fallback data should errors
   * be encountered.
   * 
   * @param resumeSequence
   *            a function that returns an Observable that will take over if the source Observable
   *            encounters an error
   * @return the original Observable, with appropriately modified behavior
   */
  def onErrorResumeNext[U >: T](resumeSequence: Observable[U]): Observable[U] = {
    val rSeq1: rx.Observable[_ <: U] = resumeSequence.asJava
    val rSeq2: rx.Observable[Nothing] = rSeq1.asInstanceOf[rx.Observable[Nothing]]
    Observable[U](asJava.onErrorResumeNext(rSeq2))
  }

  /**
   * Instruct an Observable to pass control to another Observable rather than invoking {@link Observer#onError onError} if it encounters an error of type {@link java.lang.Exception}.
   * <p>
   * This differs from {@link #onErrorResumeNext} in that this one does not handle {@link java.lang.Throwable} or {@link java.lang.Error} but lets those continue through.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/onErrorResumeNext.png">
   * <p>
   * By default, when an Observable encounters an error that prevents it from emitting the
   * expected item to its {@link Observer}, the Observable invokes its Observer's
   * <code>onError</code> method, and then quits without invoking any more of its Observer's
   * methods. The <code>onErrorResumeNext</code> method changes this behavior. If you pass
   * another Observable (<code>resumeSequence</code>) to an Observable's
   * <code>onErrorResumeNext</code> method, if the original Observable encounters an error,
   * instead of invoking its Observer's <code>onError</code> method, it will instead relinquish
   * control to <code>resumeSequence</code> which will invoke the Observer's {@link Observer#onNext onNext} method if it is able to do so. In such a case, because no
   * Observable necessarily invokes <code>onError</code>, the Observer may never know that an
   * error happened.
   * <p>
   * You can use this to prevent errors from propagating or to supply fallback data should errors
   * be encountered.
   * 
   * @param resumeSequence
   *            a function that returns an Observable that will take over if the source Observable
   *            encounters an error
   * @return the original Observable, with appropriately modified behavior
   */ 
  def onExceptionResumeNext[U >: T](resumeSequence: Observable[U]): Observable[U] = {
    val rSeq1: rx.Observable[_ <: U] = resumeSequence.asJava
    val rSeq2: rx.Observable[Nothing] = rSeq1.asInstanceOf[rx.Observable[Nothing]]
    Observable[U](asJava.onExceptionResumeNext(rSeq2))
  }

  /**
   * Instruct an Observable to emit an item (returned by a specified function) rather than
   * invoking {@link Observer#onError onError} if it encounters an error.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/onErrorReturn.png">
   * <p>
   * By default, when an Observable encounters an error that prevents it from emitting the
   * expected item to its {@link Observer}, the Observable invokes its Observer's
   * <code>onError</code> method, and then quits without invoking any more of its Observer's
   * methods. The <code>onErrorReturn</code> method changes this behavior. If you pass a function
   * (<code>resumeFunction</code>) to an Observable's <code>onErrorReturn</code> method, if the
   * original Observable encounters an error, instead of invoking its Observer's
   * <code>onError</code> method, it will instead pass the return value of
   * <code>resumeFunction</code> to the Observer's {@link Observer#onNext onNext} method.
   * <p>
   * You can use this to prevent errors from propagating or to supply fallback data should errors
   * be encountered.
   * 
   * @param resumeFunction
   *            a function that returns an item that the new Observable will emit if the source
   *            Observable encounters an error
   * @return the original Observable with appropriately modified behavior
   */
  def onErrorReturn[U >: T](resumeFunction: Throwable => U): Observable[U] = {
    val f1: Func1[Throwable, _ <: U] = resumeFunction
    val f2 = f1.asInstanceOf[Func1[Throwable, Nothing]]
    Observable[U](asJava.onErrorReturn(f2))
  } 

  /**
   * Returns an Observable that applies a function of your choosing to the first item emitted by a
   * source Observable, then feeds the result of that function along with the second item emitted
   * by the source Observable into the same function, and so on until all items have been emitted
   * by the source Observable, and emits the final result from the final call to your function as
   * its sole item.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/reduce.png">
   * <p>
   * This technique, which is called "reduce" or "aggregate" here, is sometimes called "fold,"
   * "accumulate," "compress," or "inject" in other programming contexts. Groovy, for instance,
   * has an <code>inject</code> method that does a similar operation on lists.
   * 
   * @param accumulator
   *            An accumulator function to be invoked on each item emitted by the source
   *            Observable, whose result will be used in the next accumulator call
   * @return an Observable that emits a single item that is the result of accumulating the
   *         output from the source Observable
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh229154(v%3Dvs.103).aspx">MSDN: Observable.Aggregate</a>
   * @see <a href="http://en.wikipedia.org/wiki/Fold_(higher-order_function)">Wikipedia: Fold (higher-order function)</a>
   */
  def reduce[U >: T](f: (U, U) => U): Observable[U] = {
    val func: Func2[_ >: U, _ >: U, _ <: U] = f
    val func2 = func.asInstanceOf[Func2[T, T, T]]
    Observable[U](asJava.asInstanceOf[rx.Observable[T]].reduce(func2))
  } 

  /**
   * Returns a {@link ConnectableObservable} that shares a single subscription to the underlying
   * Observable that will replay all of its items and notifications to any future {@link Observer}.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/replay.png">
   * 
   * @return a pair of a start function and an {@link Observable} such that when the start function
   *         is called, the Observable starts to emit items to its {@link Observer}s
   */
  def replay: (() => Subscription, Observable[T]) = {
    val javaCO = asJava.replay()
    (() => javaCO.connect(), Observable[T](javaCO))
  }

  /**
   * This method has similar behavior to {@link #replay} except that this auto-subscribes to
   * the source Observable rather than returning a {@link ConnectableObservable}.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/cache.png">
   * <p>
   * This is useful when you want an Observable to cache responses and you can't control the
   * subscribe/unsubscribe behavior of all the {@link Observer}s.
   * <p>
   * NOTE: You sacrifice the ability to unsubscribe from the origin when you use the
   * <code>cache()</code> operator so be careful not to use this operator on Observables that
   * emit an infinite or very large number of items that will use up memory.
   * 
   * @return an Observable that when first subscribed to, caches all of its notifications for
   *         the benefit of subsequent subscribers.
   */
  def cache: Observable[T] = {
    Observable[T](asJava.cache())
  }

  /**
   * Returns a {@link ConnectableObservable}, which waits until its {@link ConnectableObservable#connect connect} method is called before it begins emitting
   * items to those {@link Observer}s that have subscribed to it.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/publishConnect.png">
   * 
   * @return a pair of a start function and an {@link Observable} such that when the start function
   *         is called, the Observable starts to emit items to its {@link Observer}s
   */
  def publish: (() => Subscription, Observable[T]) = {
    val javaCO = asJava.publish()
    (() => javaCO.connect(), Observable[T](javaCO))
  }

  // TODO add Scala-like aggregate function
  
  /**
   * Returns an Observable that applies a function of your choosing to the first item emitted by a
   * source Observable, then feeds the result of that function along with the second item emitted
   * by an Observable into the same function, and so on until all items have been emitted by the
   * source Observable, emitting the final result from the final call to your function as its sole
   * item.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/reduceSeed.png">
   * <p>
   * This technique, which is called "reduce" or "aggregate" here, is sometimes called "fold,"
   * "accumulate," "compress," or "inject" in other programming contexts. Groovy, for instance,
   * has an <code>inject</code> method that does a similar operation on lists.
   * 
   * @param initialValue
   *            the initial (seed) accumulator value
   * @param accumulator
   *            an accumulator function to be invoked on each item emitted by the source
   *            Observable, the result of which will be used in the next accumulator call
   * @return an Observable that emits a single item that is the result of accumulating the output
   *         from the items emitted by the source Observable
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh229154(v%3Dvs.103).aspx">MSDN: Observable.Aggregate</a>
   * @see <a href="http://en.wikipedia.org/wiki/Fold_(higher-order_function)">Wikipedia: Fold (higher-order function)</a>
   */
  def fold[R](initialValue: R)(accumulator: (R, T) => R): Observable[R] = {
    Observable[R](asJava.reduce(initialValue, accumulator))
  }
  
  /**
   * Returns an Observable that emits the results of sampling the items emitted by the source
   * Observable at a specified time interval.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/sample.png">
   * 
   * @param period
   *            the sampling rate
   * @param unit
   *            the {@link TimeUnit} in which <code>period</code> is defined
   * @return an Observable that emits the results of sampling the items emitted by the source
   *         Observable at the specified time interval
   */
  def sample(duration: Duration): Observable[T] = {
    Observable[T](asJava.sample(duration.length, duration.unit))
  }

  /**
   * Returns an Observable that emits the results of sampling the items emitted by the source
   * Observable at a specified time interval.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/sample.png">
   * 
   * @param period
   *            the sampling rate
   * @param unit
   *            the {@link TimeUnit} in which <code>period</code> is defined
   * @param scheduler
   *            the {@link Scheduler} to use when sampling
   * @return an Observable that emits the results of sampling the items emitted by the source
   *         Observable at the specified time interval
   */
  def sample(duration: Duration, scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.sample(duration.length, duration.unit, scheduler))
  }
  
  /**
   * Returns an Observable that applies a function of your choosing to the first item emitted by a
   * source Observable, then feeds the result of that function along with the second item emitted
   * by an Observable into the same function, and so on until all items have been emitted by the
   * source Observable, emitting the result of each of these iterations.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/scanSeed.png">
   * <p>
   * This sort of function is sometimes called an accumulator.
   * <p>
   * Note that when you pass a seed to <code>scan()</code> the resulting Observable will emit
   * that seed as its first emitted item.
   * 
   * @param initialValue
   *            the initial (seed) accumulator value
   * @param accumulator
   *            an accumulator function to be invoked on each item emitted by the source
   *            Observable, whose result will be emitted to {@link Observer}s via {@link Observer#onNext onNext} and used in the next accumulator call.
   * @return an Observable that emits the results of each call to the accumulator function
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh211665(v%3Dvs.103).aspx">MSDN: Observable.Scan</a>
   */
  def scan[R](initialValue: R)(accumulator: (R, T) => R): Observable[R] = {
    Observable[R](asJava.scan(initialValue, accumulator))
  }

  /**
   * Returns an Observable that emits a Boolean that indicates whether all of the items emitted by
   * the source Observable satisfy a condition.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/all.png">
   * 
   * @param predicate
   *            a function that evaluates an item and returns a Boolean
   * @return an Observable that emits <code>true</code> if all items emitted by the source
   *         Observable satisfy the predicate; otherwise, <code>false</code>
   */
  def forall(predicate: T => Boolean): Observable[Boolean] = {
    // type mismatch; found : rx.Observable[java.lang.Boolean] required: rx.Observable[_ <: scala.Boolean]
    // new Observable[Boolean](asJava.all(predicate))
    // it's more fun in Scala:
    this.map(predicate).fold(true)(_ && _)
  }
    
  /**
   * Returns an Observable that skips the first <code>num</code> items emitted by the source
   * Observable and emits the remainder.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/skip.png">
   * <p>
   * You can ignore the first <code>num</code> items emitted by an Observable and attend only to
   * those items that come after, by modifying the Observable with the <code>skip</code> method.
   * 
   * @param num
   *            the number of items to skip
   * @return an Observable that is identical to the source Observable except that it does not
   *         emit the first <code>num</code> items that the source emits
   */
  def drop(n: Int): Observable[T] = {
    Observable[T](asJava.skip(n))
  }

  /**
   * Returns an Observable that bypasses all items from the source Observable as long as the specified
   * condition holds true. Emits all further source items as soon as the condition becomes false.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/skipWhile.png">
   *
   * @param predicate
   *            A function to test each item emitted from the source Observable for a condition.
   * @return an Observable that emits all items from the source Observable as soon as the condition
   *         becomes false.
   */
  def dropWhile(predicate: T => Boolean): Observable[T] = {
    Observable[T](asJava.skipWhile(predicate))
  }

  /**
   * Returns an Observable that emits only the first <code>num</code> items emitted by the source
   * Observable.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/take.png">
   * <p>
   * This method returns an Observable that will invoke a subscribing {@link Observer}'s {@link Observer#onNext onNext} function a maximum of <code>num</code> times before invoking
   * {@link Observer#onCompleted onCompleted}.
   * 
   * @param num
   *            the number of items to take
   * @return an Observable that emits only the first <code>num</code> items from the source
   *         Observable, or all of the items from the source Observable if that Observable emits
   *         fewer than <code>num</code> items
   */
  def take(n: Int): Observable[T] = {
    Observable[T](asJava.take(n))
  }

  /**
   * Returns an Observable that emits items emitted by the source Observable so long as a
   * specified condition is true.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/takeWhile.png">
   * 
   * @param predicate
   *            a function that evaluates an item emitted by the source Observable and returns a
   *            Boolean
   * @return an Observable that emits the items from the source Observable so long as each item
   *         satisfies the condition defined by <code>predicate</code>
   */
  def takeWhile(predicate: T => Boolean): Observable[T] = {
    Observable[T](asJava.takeWhile(predicate))
  }
  
  /**
   * Returns an Observable that emits the items emitted by a source Observable so long as a given
   * predicate remains true, where the predicate can operate on both the item and its index
   * relative to the complete sequence.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/takeWhileWithIndex.png">
   * 
   * @param predicate
   *            a function to test each item emitted by the source Observable for a condition;
   *            the second parameter of the function represents the index of the source item
   * @return an Observable that emits items from the source Observable so long as the predicate
   *         continues to return <code>true</code> for each item, then completes
   */
  def takeWhileWithIndex(predicate: (T, Integer) => Boolean): Observable[T] = {
    Observable[T](asJava.takeWhileWithIndex(predicate))
  }

  /**
   * Returns an Observable that emits only the last <code>count</code> items emitted by the source
   * Observable.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/last.png">
   * 
   * @param count
   *            the number of items to emit from the end of the sequence emitted by the source
   *            Observable
   * @return an Observable that emits only the last <code>count</code> items emitted by the source
   *         Observable
   */
  def takeRight(n: Int): Observable[T] = {
    Observable[T](asJava.takeLast(n))
  }
  
  /**
   * Returns an Observable that emits the items from the source Observable only until the
   * <code>other</code> Observable emits an item.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/takeUntil.png">
   * 
   * @param that
   *            the Observable whose first emitted item will cause <code>takeUntil</code> to stop
   *            emitting items from the source Observable
   * @param <E>
   *            the type of items emitted by <code>other</code>
   * @return an Observable that emits the items of the source Observable until such time as
   *         <code>other</code> emits its first item
   */
  def takeUntil[E](that: Observable[E]): Observable[T] = {
    Observable[T](asJava.takeUntil(that.asJava))
  } 
 
  /**
   * Returns an Observable that emits a single item, a list composed of all the items emitted by
   * the source Observable.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/toList.png">
   * <p>
   * Normally, an Observable that returns multiple items will do so by invoking its {@link Observer}'s {@link Observer#onNext onNext} method for each such item. You can change
   * this behavior, instructing the Observable to compose a list of all of these items and then to
   * invoke the Observer's <code>onNext</code> function once, passing it the entire list, by
   * calling the Observable's <code>toList</code> method prior to calling its {@link #subscribe} method.
   * <p>
   * Be careful not to use this operator on Observables that emit infinite or very large numbers
   * of items, as you do not have the option to unsubscribe.
   * 
   * @return an Observable that emits a single item: a List containing all of the items emitted by
   *         the source Observable.
   */
  def toSeq: Observable[Seq[T]] = {
    Observable.jObsOfListToScObsOfSeq(asJava.toList())
        : Observable[Seq[T]] // SI-7818
  }
  
  /**
   * Groups the items emitted by this Observable according to a specified discriminator function.
   * 
   * @param f
   *            a function that extracts the key from an item
   * @param <K>
   *            the type of keys returned by the discriminator function.
   * @return an Observable that emits {@code (key, observable)} pairs, where {@code observable}
   *         contains all items for which {@code f} returned {@code key}.
   */
  def groupBy[K](f: T => K): Observable[(K, Observable[T])] = {
    val o1 = asJava.groupBy[K](f) : rx.Observable[_ <: rx.observables.GroupedObservable[K, _ <: T]] 
    val func = (o: rx.observables.GroupedObservable[K, _ <: T]) => (o.getKey(), Observable[T](o))
    Observable[(K, Observable[T])](o1.map[(K, Observable[T])](func))
  }
  
  /**
   * Given an Observable that emits Observables, creates a single Observable that
   * emits the items emitted by the most recently published of those Observables.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/switchDo.png">
   * 
   * @param sequenceOfSequences
   *            the source Observable that emits Observables
   * @return an Observable that emits only the items emitted by the most recently published
   *         Observable
   */
  def switch[U](implicit evidence: Observable[T] <:< Observable[Observable[U]]): Observable[U] = {
    val o2: Observable[Observable[U]] = this
    val o3: Observable[rx.Observable[_ <: U]] = o2.map(_.asJava)
    val o4: rx.Observable[_ <: rx.Observable[_ <: U]] = o3.asJava
    val o5 = rx.Observable.switchOnNext[U](o4)
    Observable[U](o5)
  }
  // Naming: We follow C# (switch), not Java (switchOnNext), because Java just had to avoid clash with keyword
  
 /**
   * Flattens two Observables into one Observable, without any transformation.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/merge.png">
   * <p>
   * You can combine items emitted by two Observables so that they act like a single
   * Observable by using the {@code merge} method.
   * 
   * @param that
   *            an Observable to be merged
   * @return an Observable that emits items from {@code this} and {@code that} until 
   *            {@code this} or {@code that} emits {@code onError} or {@code onComplete}.
   */
  def merge[U >: T](that: Observable[U]): Observable[U] = {
    val thisJava: rx.Observable[_ <: U] = this.asJava
    val thatJava: rx.Observable[_ <: U] = that.asJava
    Observable[U](rx.Observable.merge(thisJava, thatJava))
  }

  /**
   * This behaves like {@link #merge(Observable)} except that if any of the merged Observables
   * notify of an error via {@link Observer#onError onError}, {@code mergeDelayError} will
   * refrain from propagating that error notification until all of the merged Observables have
   * finished emitting items.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/mergeDelayError.png">
   * <p>
   * Even if multiple merged Observables send {@code onError} notifications, {@code mergeDelayError} will only invoke the {@code onError} method of its
   * Observers once.
   * <p>
   * This method allows an Observer to receive all successfully emitted items from all of the
   * source Observables without being interrupted by an error notification from one of them.
   *
   * @param that
   *            an Observable to be merged
   * @return an Observable that emits items that are the result of flattening the items emitted by
   *         {$code this} and {$code that}
   */
  def mergeDelayError[U >: T](that: Observable[U]): Observable[U] = {
    Observable[U](rx.Observable.mergeDelayError[U](this.asJava, that.asJava))
  }

  /**
   * Flattens the sequence of Observables emitted by {@code this} into one Observable, without any
   * transformation.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/merge.png">
   * <p>
   * You can combine the items emitted by multiple Observables so that they act like a single
   * Observable by using this method.
   *
   * @return an Observable that emits items that are the result of flattening the items emitted
   *         by the Observables emitted by {@code this}
   */
  def flatten[U](implicit evidence: Observable[T] <:< Observable[Observable[U]]): Observable[U] = {
    val o2: Observable[Observable[U]] = this
    val o3: Observable[rx.Observable[_ <: U]] = o2.map(_.asJava)
    val o4: rx.Observable[_ <: rx.Observable[_ <: U]] = o3.asJava
    val o5 = rx.Observable.merge[U](o4)
    Observable[U](o5)
  }

  /**
   * This behaves like {@link #flatten(<:<)} except that if any of the merged Observables
   * notify of an error via {@link Observer#onError onError}, this method will
   * refrain from propagating that error notification until all of the merged Observables have
   * finished emitting items.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/mergeDelayError.png">
   * <p>
   * Even if multiple merged Observables send {@code onError} notifications, this method will only invoke the {@code onError} method of its
   * Observers once.
   * <p>
   * This method allows an Observer to receive all successfully emitted items from all of the
   * source Observables without being interrupted by an error notification from one of them.
   *
   * @return an Observable that emits items that are the result of flattening the items emitted by
   *         the Observables emitted by the this Observable
   */
  def flattenDelayError[U](implicit evidence: Observable[T] <:< Observable[Observable[U]]): Observable[U] = {
    val o2: Observable[Observable[U]] = this
    val o3: Observable[rx.Observable[_ <: U]] = o2.map(_.asJava)
    val o4: rx.Observable[_ <: rx.Observable[_ <: U]] = o3.asJava
    val o5 = rx.Observable.mergeDelayError[U](o4)
    Observable[U](o5)
  }

  /**
   * Combines two observables, emitting a pair of the latest values of each of
   * the source observables each time an event is received from one of the source observables, where the
   * aggregation is defined by the given function.
   *
   * @param that
   *            The second source observable.
   * @return An Observable that combines the source Observables
   */
  def combineLatest[U](that: Observable[U]): Observable[(T, U)] = {
    val f: Func2[_ >: T, _ >: U, _ <: (T, U)] = (t: T, u: U) => (t, u)
    Observable[(T, U)](rx.Observable.combineLatest[T, U, (T, U)](this.asJava, that.asJava, f))
  }

  /**
   * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
   * <p>
   * NOTE: If events keep firing faster than the timeout then no data will be emitted.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleWithTimeout.png">
   * <p>
   * Information on debounce vs throttle:
   * <p>
   * <ul>
   * <li>http://drupalmotion.com/article/debounce-and-throttle-visual-explanation</li>
   * <li>http://unscriptable.com/2009/03/20/debouncing-javascript-methods/</li>
   * <li>http://www.illyriad.co.uk/blog/index.php/2011/09/javascript-dont-spam-your-server-debounce-and-throttle/</li>
   * </ul>
   *
   * @param timeout
   *            The time each value has to be 'the most recent' of the {@link Observable} to ensure that it's not dropped.
   *
   * @return An {@link Observable} which filters out values which are too quickly followed up with newer values.
   * @see {@link #debounce}
   */
  def throttleWithTimeout(timeout: Duration): Observable[T] = {
    Observable[T](asJava.throttleWithTimeout(timeout.length, timeout.unit))
  }

  /**
   * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
   * <p>
   * NOTE: If events keep firing faster than the timeout then no data will be emitted.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/debounce.png">
   * <p>
   * Information on debounce vs throttle:
   * <p>
   * <ul>
   * <li>http://drupalmotion.com/article/debounce-and-throttle-visual-explanation</li>
   * <li>http://unscriptable.com/2009/03/20/debouncing-javascript-methods/</li>
   * <li>http://www.illyriad.co.uk/blog/index.php/2011/09/javascript-dont-spam-your-server-debounce-and-throttle/</li>
   * </ul>
   *
   * @param timeout
   *            The time each value has to be 'the most recent' of the {@link Observable} to ensure that it's not dropped.
   *
   * @return An {@link Observable} which filters out values which are too quickly followed up with newer values.
   * @see {@link #throttleWithTimeout};
   */
  def debounce(timeout: Duration): Observable[T] = {
    Observable[T](asJava.debounce(timeout.length, timeout.unit))
  }

  /**
   * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
   * <p>
   * NOTE: If events keep firing faster than the timeout then no data will be emitted.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/debounce.png">
   * <p>
   * Information on debounce vs throttle:
   * <p>
   * <ul>
   * <li>http://drupalmotion.com/article/debounce-and-throttle-visual-explanation</li>
   * <li>http://unscriptable.com/2009/03/20/debouncing-javascript-methods/</li>
   * <li>http://www.illyriad.co.uk/blog/index.php/2011/09/javascript-dont-spam-your-server-debounce-and-throttle/</li>
   * </ul>
   *
   * @param timeout
   *            The time each value has to be 'the most recent' of the {@link Observable} to ensure that it's not dropped.
   * @param scheduler
   *            The {@link Scheduler} to use internally to manage the timers which handle timeout for each event.
   * @return Observable which performs the throttle operation.
   * @see {@link #throttleWithTimeout};
   */
  def debounce(timeout: Duration, scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.debounce(timeout.length, timeout.unit, scheduler))
  }

  /**
   * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
   * <p>
   * NOTE: If events keep firing faster than the timeout then no data will be emitted.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleWithTimeout.png">
   *
   * @param timeout
   *            The time each value has to be 'the most recent' of the {@link Observable} to ensure that it's not dropped.
   * @param scheduler
   *            The {@link Scheduler} to use internally to manage the timers which handle timeout for each event.
   * @return Observable which performs the throttle operation.
   * @see {@link #debounce}
   */
  def throttleWithTimeout(timeout: Duration, scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.throttleWithTimeout(timeout.length, timeout.unit, scheduler))
  }

  /**
   * Throttles by skipping value until `skipDuration` passes and then emits the next received value.
   * <p>
   * This differs from {@link #throttleLast} in that this only tracks passage of time whereas {@link #throttleLast} ticks at scheduled intervals.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleFirst.png">
   *
   * @param skipDuration
   *            Time to wait before sending another value after emitting last value.
   * @param scheduler
   *            The {@link Scheduler} to use internally to manage the timers which handle timeout for each event.
   * @return Observable which performs the throttle operation.
   */
  def throttleFirst(skipDuration: Duration, scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.throttleFirst(skipDuration.length, skipDuration.unit, scheduler))
  }

  /**
   * Throttles by skipping value until `skipDuration` passes and then emits the next received value.
   * <p>
   * This differs from {@link #throttleLast} in that this only tracks passage of time whereas {@link #throttleLast} ticks at scheduled intervals.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleFirst.png">
   *
   * @param skipDuration
   *            Time to wait before sending another value after emitting last value.
   * @return Observable which performs the throttle operation.
   */
  def throttleFirst(skipDuration: Duration): Observable[T] = {
    Observable[T](asJava.throttleFirst(skipDuration.length, skipDuration.unit))
  }

  /**
   * Throttles by returning the last value of each interval defined by 'intervalDuration'.
   * <p>
   * This differs from {@link #throttleFirst} in that this ticks along at a scheduled interval whereas {@link #throttleFirst} does not tick, it just tracks passage of time.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleLast.png">
   *
   * @param intervalDuration
   *            Duration of windows within with the last value will be chosen.
   * @return Observable which performs the throttle operation.
   * @see {@link #sample(long, TimeUnit)}
   */
  def throttleLast(intervalDuration: Duration): Observable[T] = {
    Observable[T](asJava.throttleLast(intervalDuration.length, intervalDuration.unit))
  }

  /**
   * Throttles by returning the last value of each interval defined by 'intervalDuration'.
   * <p>
   * This differs from {@link #throttleFirst} in that this ticks along at a scheduled interval whereas {@link #throttleFirst} does not tick, it just tracks passage of time.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/throttleLast.png">
   *
   * @param intervalDuration
   *            Duration of windows within with the last value will be chosen.
   * @return Observable which performs the throttle operation.
   * @see {@link #sample(long, TimeUnit, Scheduler)}
   */
  def throttleLast(intervalDuration: Duration, scheduler: Scheduler): Observable[T] = {
    Observable[T](asJava.throttleLast(intervalDuration.length, intervalDuration.unit, scheduler))
  }

  /**
   * Returns an Observable that sums up the elements of this Observable.
   * 
   * @return an Observable emitting the sum of all the elements of the source Observable
   *         as its single item.
   */
  def sum[U >: T](implicit num: Numeric[U]): Observable[U] = {
    fold(num.zero)(num.plus)
  }
  
  /**
   * Returns an Observable that multiplies up the elements of this Observable.
   * 
   * @return an Observable emitting the product of all the elements of the source Observable
   *         as its single item.
   */
  def product[U >: T](implicit num: Numeric[U]): Observable[U] = {
    fold(num.one)(num.times)
  }

  /**
   * Returns an Observable that emits only the very first item emitted by the source Observable, or
   * a default value if the source Observable is empty.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/firstOrDefault.png">
   *
   * @param defaultValue
   *            The default value to emit if the source Observable doesn't emit anything.
   *            This is a by-name parameter, so it is only evaluated if the source Observable doesn't emit anything.
   * @return an Observable that emits only the very first item from the source, or a default value
   *         if the source Observable completes without emitting any item.
   */
  def firstOrElse[U >: T](default: => U): Observable[U] = {
    this.take(1).fold[Option[U]](None)((v: Option[U], e: U) => Some(e)).map({
      case Some(element) => element
      case None => default
    })
  }

  /**
   * Returns an Observable that emits only the very first item emitted by the source Observable.
   * This is just a shorthand for {@code take(1)}.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/first.png">
   *
   * @return an Observable that emits only the very first item from the source, or none if the
   *         source Observable completes without emitting a single item.
   */
  def first: Observable[T] = {
    take(1)
  }

  /**
   * Returns an Observable that forwards all sequentially distinct items emitted from the source Observable.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinctUntilChanged.png">
   *
   * @return an Observable of sequentially distinct items
   */
  def distinctUntilChanged: Observable[T] = {
    Observable[T](asJava.distinctUntilChanged)
  }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
   * distinct according to a key selector function.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinctUntilChanged.key.png">
   *
   * @param keySelector
   *            a function that projects an emitted item to a key value which is used for deciding whether an item is sequentially
   *            distinct from another one or not
   * @return an Observable of sequentially distinct items
   */
  def distinctUntilChanged[U](keySelector: T => U): Observable[T] = {
    Observable[T](asJava.distinctUntilChanged[U](keySelector))
  }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
   * distinct according to an equality function.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinctUntilChanged.png">
   *
   * @param equality
   *            an equality function for deciding whether two emitted items are equal or not
   * @return an Observable of sequentially distinct items
   */
  // def distinctUntilChanged[U](equality: (T, T) => Boolean): Observable[T] = {
  //   TODO once https://github.com/Netflix/RxJava/issues/395 is fixed
  // }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
   * distinct according to a key selector function and a comparator.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinctUntilChanged.key.png">
   *
   * @param keySelector
   *            a function that projects an emitted item to a key value which is used for deciding whether an item is sequentially
   *            distinct from another one or not
   * @param equality
   *            an equality function for deciding whether two emitted item keys are equal or not
   * @return an Observable of sequentially distinct items
   */
  // def distinctUntilChanged[U](keySelector: T => U, equality: (T, T) => Boolean): Observable[T] = {
  //   TODO once https://github.com/Netflix/RxJava/issues/395 is fixed
  // }

  /**
   * Returns an Observable that forwards all distinct items emitted from the source Observable.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinct.png">
   *
   * @return an Observable of distinct items
   */
  def distinct: Observable[T] = {
    Observable[T](asJava.distinct())
  }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
   * to a comparator.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinct.png">
   *
   * @param equality
   *            an equality function for deciding whether two emitted items are equal or not
   * @return an Observable of distinct items
   */
  // def distinct(equality: (T, T) => Boolean): Observable[T] = {
  //   TODO once https://github.com/Netflix/RxJava/issues/395 is fixed
  // }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
   * to a key selector function.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinct.key.png">
   *
   * @param keySelector
   *            a function that projects an emitted item to a key value which is used for deciding whether an item is
   *            distinct from another one or not
   * @return an Observable of distinct items
   */
  def distinct[U](keySelector: T => U): Observable[T] = {
    Observable[T](asJava.distinct[U](keySelector))
  }

  /**
   * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
   * to a key selector function and a comparator.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/distinct.key.png">
   *
   * @param keySelector
   *            a function that projects an emitted item to a key value which is used for deciding whether an item is
   *            distinct from another one or not
   * @param equality
   *            an equality function for deciding whether two emitted item keys are equal or not
   * @return an Observable of distinct items
   * @see <a href="http://msdn.microsoft.com/en-us/library/hh229050(v=vs.103).aspx">MSDN: Observable.distinct</a>
   */
  // def distinct[U](keySelector: T => U, equality: (T, T) => Boolean): Observable[T] = {
  //   TODO once https://github.com/Netflix/RxJava/issues/395 is fixed
  //}

  /**
   * Returns an Observable that counts the total number of elements in the source Observable.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/count.png">
   *
   * @return an Observable emitting the number of counted elements of the source Observable
   *         as its single item.
   */
  def length: Observable[Int] = {
    Observable[Integer](asJava.count()).map(_.intValue())
  }

  /**
   * Retry subscription to origin Observable upto given retry count.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/retry.png">
   * <p>
   * If {@link Observer#onError} is invoked the source Observable will be re-subscribed to as many times as defined by retryCount.
   * <p>
   * Any {@link Observer#onNext} calls received on each attempt will be emitted and concatenated together.
   * <p>
   * For example, if an Observable fails on first time but emits [1, 2] then succeeds the second time and
   * emits [1, 2, 3, 4, 5] then the complete output would be [1, 2, 1, 2, 3, 4, 5, onCompleted].
   *
   * @param retryCount
   *            Number of retry attempts before failing.
   * @return Observable with retry logic.
   */
  def retry(retryCount: Int): Observable[T] = {
    Observable[T](asJava.retry(retryCount))
  }

  /**
   * Retry subscription to origin Observable whenever onError is called (infinite retry count).
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/retry.png">
   * <p>
   * If {@link Observer#onError} is invoked the source Observable will be re-subscribed to.
   * <p>
   * Any {@link Observer#onNext} calls received on each attempt will be emitted and concatenated together.
   * <p>
   * For example, if an Observable fails on first time but emits [1, 2] then succeeds the second time and
   * emits [1, 2, 3, 4, 5] then the complete output would be [1, 2, 1, 2, 3, 4, 5, onCompleted].
   * @return Observable with retry logic.
   */
  def retry: Observable[T] = {
    Observable[T](asJava.retry())
  }
  
  /**
   * Converts an Observable into a {@link BlockingObservable} (an Observable with blocking
   * operators).
   *
   * @see <a href="https://github.com/Netflix/RxJava/wiki/Blocking-Observable-Operators">Blocking Observable Operators</a>
   */
  def toBlockingObservable: BlockingObservable[T] = {
    new BlockingObservable[T](asJava.toBlockingObservable())
  }

  /**
   * Perform work in parallel by sharding an {@code Observable<T>} on a {@link Schedulers#threadPoolForComputation()} {@link Scheduler} and return an {@code Observable<R>} with the output.
   *
   * @param f
   *            a {@link Func1} that applies Observable operators to {@code Observable<T>} in parallel and returns an {@code Observable<R>}
   * @return an Observable with the output of the function executed on a {@link Scheduler}
   */
  def parallel[R](f: Observable[T] => Observable[R]): Observable[R] = {
    val fJava: Func1[rx.Observable[T], rx.Observable[R]] =
      (jo: rx.Observable[T]) => f(Observable[T](jo)).asJava.asInstanceOf[rx.Observable[R]]
    Observable[R](asJava.asInstanceOf[rx.Observable[T]].parallel[R](fJava))
  }

  /**
   * Perform work in parallel by sharding an {@code Observable<T>} on a {@link Scheduler} and return an {@code Observable<R>} with the output.
   *
   * @param f
   *            a {@link Func1} that applies Observable operators to {@code Observable<T>} in parallel and returns an {@code Observable<R>}
   * @param s
   *            a {@link Scheduler} to perform the work on.
   * @return an Observable with the output of the {@link Func1} executed on a {@link Scheduler}
   */
  def parallel[R](f: Observable[T] => Observable[R], scheduler: Scheduler): Observable[R] = {
    val fJava: Func1[rx.Observable[T], rx.Observable[R]] =
      (jo: rx.Observable[T]) => f(Observable[T](jo)).asJava.asInstanceOf[rx.Observable[R]]
    Observable[R](asJava.asInstanceOf[rx.Observable[T]].parallel[R](fJava, scheduler))
  }  
  
  def withFilter(p: T => Boolean): WithFilter[T] = {
    new WithFilter[T](p, asJava)
  }
  
}

object Observable {
  import scala.collection.JavaConverters._
  import scala.collection.immutable.Range
  import scala.concurrent.duration.Duration
  import rx.{Observable => JObservable}
  import rx.lang.scala.{Notification, Subscription, Scheduler, Observer}
  import rx.lang.scala.util._
  import rx.util.functions._
  import rx.lang.scala.ImplicitFunctionConversions._
 
  private[scala] 
  def jObsOfListToScObsOfSeq[T](jObs: rx.Observable[_ <: java.util.List[T]]): Observable[Seq[T]] = {
    val oScala1: Observable[java.util.List[T]] = new Observable[java.util.List[T]](jObs)
    oScala1.map((lJava: java.util.List[T]) => lJava.asScala)
  }
  
  private[scala] 
  def jObsOfJObsToScObsOfScObs[T](jObs: rx.Observable[_ <: rx.Observable[_ <: T]]): Observable[Observable[T]] = {
    val oScala1: Observable[rx.Observable[_ <: T]] = new Observable[rx.Observable[_ <: T]](jObs)
    oScala1.map((oJava: rx.Observable[_ <: T]) => new Observable[T](oJava))
  }
   
  def apply[T](asJava: rx.Observable[_ <: T]): Observable[T] = {
    new Observable[T](asJava)
  }
  
  /**
   * Creates an Observable that will execute the given function when an {@link Observer} subscribes to it.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/create.png">
   * <p>
   * Write the function you pass to <code>create</code> so that it behaves as an Observable: It
   * should invoke the Observer's {@link Observer#onNext onNext}, {@link Observer#onError onError}, and {@link Observer#onCompleted onCompleted} methods
   * appropriately.
   * <p>
   * A well-formed Observable must invoke either the Observer's <code>onCompleted</code> method
   * exactly once or its <code>onError</code> method exactly once.
   * <p>
   * See <a href="http://go.microsoft.com/fwlink/?LinkID=205219">Rx Design Guidelines (PDF)</a>
   * for detailed information.
   * 
   * @param <T>
   *            the type of the items that this Observable emits
   * @param func
   *            a function that accepts an {@code Observer<T>}, invokes its {@code onNext}, {@code onError}, and {@code onCompleted} methods
   *            as appropriate, and returns a {@link Subscription} to allow the Observer to
   *            canceling the subscription
   * @return an Observable that, when an {@link Observer} subscribes to it, will execute the given
   *         function
   */
  def apply[T](func: Observer[T] => Subscription): Observable[T] = {
    Observable[T](JObservable.create(func))
  }
  
  /**
   * Returns an Observable that invokes an {@link Observer}'s {@link Observer#onError onError} method when the Observer subscribes to it
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/error.png">
   * 
   * @param exception
   *            the particular error to report
   * @param <T>
   *            the type of the items (ostensibly) emitted by the Observable
   * @return an Observable that invokes the {@link Observer}'s {@link Observer#onError onError} method when the Observer subscribes to it
   */
  def apply[T](exception: Throwable): Observable[T] = {
    Observable[T](JObservable.error(exception))
  }

  /**
   * Converts a sequence of values into an Observable.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/from.png">
   * 
   * <p>Implementation note: the entire array will be immediately emitted each time an {@link Observer} subscribes. Since this occurs before the {@link Subscription} is returned,
   * it in not possible to unsubscribe from the sequence before it completes.
   * 
   * @param items
   *            the source Array
   * @param <T>
   *            the type of items in the Array, and the type of items to be emitted by the
   *            resulting Observable
   * @return an Observable that emits each item in the source Array
   */
  def apply[T](args: T*): Observable[T] = {     
    Observable[T](JObservable.from(args.toIterable.asJava))
  }
  
  def apply(range: Range): Observable[Int] = {
    Observable[Int](JObservable.from(range.toIterable.asJava))
  }
  
  /**
   * Returns an Observable that calls an Observable factory to create its Observable for each
   * new Observer that subscribes. That is, for each subscriber, the actuall Observable is determined
   * by the factory function.
   * 
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/defer.png">
   * <p>
   * The defer operator allows you to defer or delay emitting items from an Observable until such
   * time as an Observer subscribes to the Observable. This allows an {@link Observer} to easily
   * obtain updates or a refreshed version of the sequence.
   * 
   * @param observableFactory
   *            the Observable factory function to invoke for each {@link Observer} that
   *            subscribes to the resulting Observable
   * @param <T>
   *            the type of the items emitted by the Observable
   * @return an Observable whose {@link Observer}s trigger an invocation of the given Observable
   *         factory function
   */
  def defer[T](observable: => Observable[T]): Observable[T] = {
    Observable[T](JObservable.defer(observable.asJava))
  }

  /**
   * Returns an Observable that emits a single item and then completes.
   * <p>
   * <img width="640" src="https://raw.github.com/wiki/Netflix/RxJava/images/rx-operators/just.png">
   * <p>
   * To convert any object into an Observable that emits that object, pass that object into the
   * <code>just</code> method.
   * <p>
   * This is similar to the {@link #apply(Iterable[T])} method, except that
   * {@link #apply(Iterable[T])} will convert an {@link Iterable} object into an Observable that emits
   * each of the items in the Iterable, one at a time, while the <code>just()</code> method
   * converts an Iterable into an Observable that emits the entire Iterable as a single item.
   * 
   * @param value
   *            the item to pass to the {@link Observer}'s {@link Observer#onNext onNext} method
   * @param <T>
   *            the type of that item
   * @return an Observable that emits a single item and then completes
   */
  def just[T](value: T): Observable[T] = {
    Observable[T](JObservable.just(value))
  }
  
  /**
   * Returns an Observable that never sends any items or notifications to an {@link Observer}.
   * <p>
   * <img width="640" src="https://github.com/Netflix/RxJava/wiki/images/rx-operators/never.png">
   * <p>
   * This Observable is useful primarily for testing purposes.
   * 
   * @return an Observable that never sends any items or notifications to an {@link Observer}
   */
  def never: Observable[Nothing] = {
    Observable[Nothing](JObservable.never())
  }

  // TODO also support Scala Futures, but think well before. Do we want to Future and Observable
  // to share a common base interface?
  
  // private because it's not RxScala's responsability to provide this alias
  private type Future[+T] = java.util.concurrent.Future[_ <: T]
  
  def apply[T](f: Future[T]): Observable[T] = {
    Observable[T](rx.Observable.from(f))
  } 

  def apply[T](f: Future[T], scheduler: Scheduler): Observable[T] = {
    Observable[T](rx.Observable.from(f, scheduler))
  }

  def apply[T](f: Future[T], duration: Duration): Observable[T] = {
    Observable[T](rx.Observable.from(f, duration.length, duration.unit))
  }

  /**
   * Given a Seq of N observables, returns an observable that emits Seqs of N elements each.
   * The first emitted Seq will contain the first element of each source observable,
   * the second Seq the second element of each source observable, and so on.
   * 
   * @param observables
   *            A Seq of source Observables
   * @return an Observable that emits the zipped Seqs
   */
  def zip[T](observables: Seq[Observable[T]]): Observable[Seq[T]] = {
    val f: FuncN[Seq[T]] = (args: Seq[java.lang.Object]) => {
      val asSeq: Seq[Object] = args.toSeq
      asSeq.asInstanceOf[Seq[T]]
    }
    val list = observables.map(_.asJava).asJava
    val o = rx.Observable.zip(list, f)
    Observable[Seq[T]](o)
  }
  
  /**
   * Given an Observable emitting N source observables, returns an observable that emits Seqs of N elements each.
   * The first emitted Seq will contain the first element of each source observable,
   * the second Seq the second element of each source observable, and so on.
   * 
   * @param observables
   *            An Observable emitting N source Observables
   * @return an Observable that emits the zipped Seqs
   */
  def zip[T](observables: Observable[Observable[T]]): Observable[Seq[T]] = {
    val f: FuncN[Seq[T]] = (args: Seq[java.lang.Object]) => {
      val asSeq: Seq[Object] = args.toSeq
      asSeq.asInstanceOf[Seq[T]]
    }
    val list = observables.map(_.asJava).asJava
    val o = rx.Observable.zip(list, f)
    Observable[Seq[T]](o)
  }
  
  def interval(duration: Duration): Observable[Long] = {
    (new Observable[java.lang.Long](JObservable.interval(duration.length, duration.unit))).map(_.longValue())
  }
  
  def interval(duration: Duration, scheduler: Scheduler): Observable[Long] = {
    (new Observable[java.lang.Long](JObservable.interval(duration.length, duration.unit, scheduler))).map(_.longValue())
  }
  
}

// Cannot yet have inner class because of this error message: 
// "implementation restriction: nested class is not allowed in value class.
// This restriction is planned to be removed in subsequent releases."  
class WithFilter[+T] private[scala] (p: T => Boolean, asJava: rx.Observable[_ <: T]) {
  import rx.lang.scala.ImplicitFunctionConversions._
  
  def map[B](f: T => B): Observable[B] = {
    Observable[B](asJava.filter(p).map[B](f))
  }
  
  def flatMap[B](f: T => Observable[B]): Observable[B] = {
    Observable[B](asJava.filter(p).flatMap[B]((x: T) => f(x).asJava))
  }
  
  def withFilter(q: T => Boolean): Observable[T] = {
    Observable[T](asJava.filter((x: T) => p(x) && q(x)))
  }
  
  // there is no foreach here, that's only available on BlockingObservable
}

class UnitTestSuite extends org.scalatest.junit.JUnitSuite {
  import scala.concurrent.duration._
  import org.junit.{Before, Test, Ignore}
  import org.junit.Assert._
  import org.mockito.Matchers.any
  import org.mockito.Mockito._
  import org.mockito.{ MockitoAnnotations, Mock }
  
  // Tests which needn't be run:
  
  @Ignore def testCovariance = {
    println("hey, you shouldn't run this test")
    
    val o1: Observable[Nothing] = Observable()
    val o2: Observable[Int] = o1
    val o3: Observable[App] = o1
    val o4: Observable[Any] = o2
    val o5: Observable[Any] = o3
  }
  
  // Tests which have to be run:
  
  @Test def testDematerialize() {
    val o = Observable(1, 2, 3)
    val mat = o.materialize
    val demat = mat.dematerialize
    
    // correctly rejected:
    // val wrongDemat = Observable("hello").dematerialize
    
    assertEquals(demat.toBlockingObservable.toIterable.toList, List(1, 2, 3))
  }
    
  // Test that Java's firstOrDefault propagates errors.
  // If this changes (i.e. it suppresses errors and returns default) then Scala's firstOrElse
  // should be changed accordingly.
  @Test def testJavaFirstOrDefault() {
    assertEquals(1, rx.Observable.from(1, 2).firstOrDefault(10).toBlockingObservable().single)
    assertEquals(10, rx.Observable.empty().firstOrDefault(10).toBlockingObservable().single)
    val msg = "msg6251"
    var receivedMsg = "none"
    try {
      rx.Observable.error(new Exception(msg)).firstOrDefault(10).toBlockingObservable().single
    } catch {
      case e: Exception => receivedMsg = e.getCause().getMessage()
    }
    assertEquals(receivedMsg, msg)
  }
  
  @Test def testFirstOrElse() {
    def mustNotBeCalled: String = error("this method should not be called")
    def mustBeCalled: String = "this is the default value"
    assertEquals("hello", Observable("hello").firstOrElse(mustNotBeCalled).toBlockingObservable.single)
    assertEquals("this is the default value", Observable().firstOrElse(mustBeCalled).toBlockingObservable.single)
  }
  
  @Test def testFirstOrElseWithError() {
    val msg = "msg6251"
    var receivedMsg = "none"
    try {
      Observable[Int](new Exception(msg)).firstOrElse(10).toBlockingObservable.single
    } catch {
      case e: Exception => receivedMsg = e.getCause().getMessage()
    }
    assertEquals(receivedMsg, msg)
  }
  
  @Test def testTest() = {
    val a: Observable[Int] = Observable()
    assertEquals(4, Observable(1, 2, 3, 4).toBlockingObservable.toIterable.last)
  }
  
}
