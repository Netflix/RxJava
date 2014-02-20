(ns rx.lang.clojure.core
  (:refer-clojure :exclude [concat cons count cycle
                            distinct do drop drop-while
                            empty every?
                            filter first future
                            interpose into
                            keep keep-indexed
                            map mapcat map-indexed
                            merge next nth partition reduce reductions
                            rest seq some sort sort-by split-with
                            take take-while throw])
  (:require [rx.lang.clojure.interop :as iop]
            [rx.lang.clojure.graph :as graph]
            [rx.lang.clojure.realized :as realized])
  (:import [rx
            Observable
            Observer Observable$Operator Observable$OnSubscribe
            Subscriber Subscription]
           [rx.observables BlockingObservable]
           [rx.subscriptions Subscriptions]
           [rx.util.functions Action0 Action1 Func0 Func1 Func2]))

(set! *warn-on-reflection* true)

(declare concat map map-indexed reduce take take-while)

(defn ^Func1 fn->predicate
  "Turn f into a predicate that returns true/false like Rx predicates should"
  [f]
  (iop/fn* (comp boolean f)))

;################################################################################

(defn observable?
  "Returns true if o is an rx.Observable"
  [o]
  (instance? Observable o))

;################################################################################

(defn on-next
  "Call onNext on the given observer."
  [^Observer o value]
  (.onNext o value))

(defn on-completed
  "Call onCompleted on the given observer."
  [^Observer o]
  (.onCompleted o))

(defn on-error
  "Call onError on the given observer."
  [^Observer o e]
  (.onError o e))

;################################################################################
; Tools for creating new operators and observables

(declare unsubscribed?)

(defn ^Subscriber subscriber
  ""
  ([o on-next-action] (subscriber o on-next-action nil nil))
  ([o on-next-action on-error-action] (subscriber o on-next-action on-error-action nil))
  ([^Subscriber o on-next-action on-error-action on-completed-action]
   (proxy [Subscriber] [o]
     (onCompleted []
       (if on-completed-action
         (on-completed-action o)
         (on-completed o)))
     (onError [e]
       (if on-error-action
         (on-error-action o e)
         (on-error o e)))
     (onNext [t]
       (if on-next-action
         (on-next-action o t)
         (on-next o t))))))

(defn ^Subscription subscription
  "Create a new subscription that calls the given no-arg handler function when
  unsubscribe is called

  See:
    rx.subscriptions.Subscriptions/create
  "
  [handler]
  (Subscriptions/create ^Action0 (iop/action* handler)))

(defn ^Observable$Operator operator*
  "Returns a new implementation of rx.Observable$Operator that calls the given
  function with a rx.Subscriber. The function should return a rx.Subscriber.

  See:
    lift
    rx.Observable$Operator
  "
  [f]
  {:pre [(fn? f)]}
  (reify Observable$Operator
    (call [this o]
      (f o))))

(defn ^Observable observable*
  "Create an Observable from the given function.

  When subscribed to, (f subscriber) is called at which point, f can start emitting values, etc.
  The passed subscriber is of type rx.Subscriber.

  See:
    rx.Subscriber
    rx.Observable/create
  "
  [f]
  (Observable/create ^Observable$OnSubscribe (iop/action* f)))

(defn wrap-on-completed
  "Wrap handler with code that automaticaly calls rx.Observable.onCompleted."
  [handler]
  (fn [^Observer observer]
    (handler observer)
    (when-not (unsubscribed? observer)
      (.onCompleted observer))))

(defn wrap-on-error
  "Wrap handler with code that automaticaly calls (on-error) if an exception is thrown"
  [handler]
  (fn [^Observer observer]
    (try
      (handler observer)
      (catch Throwable e
        (when-not (unsubscribed? observer)
          (.onError observer e))))))

(defn lift
  "Lift the Operator op over the given Observable xs

  Example:

    (->> my-observable
         (rx/lift (rx/operator ...))
         ...)

  See:
    rx.Observable/lift
    operator
  "
  [^Observable$Operator op ^Observable xs]
  (.lift xs op))

;################################################################################

(defn ^Subscription subscribe

  ([^Observable o on-next-action]
    (.subscribe o
                ^Action1 (iop/action* on-next-action)))

  ([^Observable o on-next-action on-error-action]
    (.subscribe o
                ^Action1 (iop/action* on-next-action)
                ^Action1 (iop/action* on-error-action)))

  ([^Observable o on-next-action on-error-action on-completed-action]
    (.subscribe o
                ^Action1 (iop/action* on-next-action)
                ^Action1 (iop/action* on-error-action)
                ^Action0 (iop/action* on-completed-action))))

(defn unsubscribe
  "Unsubscribe from Subscription s and return it."
  [^Subscription s]
  (.unsubscribe s)
  s)

(defn subscribe-on
  "Cause subscriptions to the given observable to happen on the given scheduler.

  Returns a new Observable.

  See:
    rx.Observable/subscribeOn
  "
  [^rx.Scheduler s ^Observable xs]
  (.subscribeOn xs s))

(defn unsubscribe-on
  "Cause unsubscriptions from the given observable to happen on the given scheduler.

  Returns a new Observable.

  See:
    rx.Observable/unsubscribeOn
  "
  [^rx.Scheduler s ^Observable xs]
  (.unsubscribeOn xs s))

(defn unsubscribed?
  "Returns true if the given Subscription (or Subscriber) is unsubscribed.

  See:
    rx.Observable/create
    observable*
  "
  [^Subscription s]
  (.isUnsubscribed s))

;################################################################################
; Functions for creating Observables

(defn ^Observable never
  "Returns an Observable that never emits any values and never completes.

  See:
    rx.Observable/never
  "
  []
  (Observable/never))

(defn ^Observable empty
  "Returns an Observable that completes immediately without emitting any values.

  See:
    rx.Observable/empty
  "
  []
  (Observable/empty))

(defn ^Observable return
  "Returns an observable that emits a single value.

  See:
    rx.Observable/just
  "
  [value]
  (Observable/just value))

(defn ^Observable seq->o
  "Make an observable out of some seq-able thing. The rx equivalent of clojure.core/seq."
  [xs]
  (if xs
    (Observable/from ^Iterable xs)
    (empty)))

;################################################################################
; Operators

(defn synchronize
  ([^Observable xs]
  (.synchronize xs))
  ([lock ^Observable xs]
  (.synchronize xs lock)))

(defn ^Observable merge
  "Observable.merge, renamed because merge means something else in Clojure

  os is one of:

    * An Iterable of Observables to merge
    * An Observable<Observable<T>> to merge

  If you want clojure.core/merge, it's just this:

    (rx/reduce clojure.core/merge {} maps)

  "
  [os]
  (cond
    (instance? Iterable os)
      (Observable/merge (Observable/from ^Iterable os))
    (instance? Observable os)
      (Observable/merge ^Observable os)
    :else
      (throw (IllegalArgumentException. (str "Don't know how to merge " (type os))))))

(defn ^Observable merge-delay-error
  "Observable.mergeDelayError"
  [os]
  (cond
    (instance? java.util.List os)
      (Observable/mergeDelayError ^java.util.List os)
    (instance? Observable os)
      (Observable/mergeDelayError ^Observable os)
    :else
      (throw (IllegalArgumentException. (str "Don't know how to merge " (type os))))))


(defn cache
  "caches the observable value so that multiple subscribers don't re-evaluate it.

  See:
    rx.Observable/cache"
  [^Observable xs]
  (.cache xs))

(defn cons
  "cons x to the beginning of xs"
  [x xs]
  (concat (return x) xs))

(defn ^Observable concat
  "Concatenate the given Observables one after the another.

  Note that xs is separate Observables which are concatentated. To concatenate an
  Observable of Observables, use concat*

  See:
    rx.Observable/concat
    concat*
  "
  [& xs]
  (Observable/concat (seq->o xs)))

(defn ^Observable concat*
  "Concatenate the given Observable of Observables one after another.

  See:
    rx.Observable/concat
    concat
  "
  [^Observable os]
  (Observable/concat os))

(defn count
  "Returns an Observable that emits the number of items is xs as a long.

  See:
    rx.Observable/longCount
  "
  [^Observable xs]
  (.longCount xs))

(defn cycle
  "Returns an Observable that emits the items of xs repeatedly, forever.

  TODO: Other sigs.

  See:
    rx.Observable/repeat
    clojure.core/cycle
  "
  [^Observable xs]
  (.repeat xs))

(defn distinct
  "Returns an Observable of the elements of Observable xs with duplicates
  removed. key-fn, if provided, is a one arg function that determines the
  key used to determined duplicates. key-fn defaults to identity.

  This implementation doesn't use rx.Observable/distinct because it doesn't
  honor Clojure's equality semantics.

  See:
    clojure.core/distinct
  "
  ([xs] (distinct identity xs))
  ([key-fn ^Observable xs]
   (let [op (operator* (fn [o]
                         (let [seen (atom #{})]
                           (subscriber o
                                       (fn [o v]
                                         (let [key (key-fn v)]
                                           (when-not (contains? @seen key)
                                             (swap! seen conj key)
                                             (on-next o v))))))))]
    (lift op xs))))

(defn ^Observable do
  "Returns a new Observable that, for each x in Observable xs, executes (do-fn x),
  presumably for its side effects, and then passes x along unchanged.

  If do-fn throws an exception, that exception is emitted via onError and the sequence
  is finished.

  Example:

    (->> (rx/seq->o [1 2 3])
    (rx/do println)
    ...)

  Will print 1, 2, 3.

  See:
    rx.Observable/doOnNext
  "
  [do-fn ^Observable xs]
  (.doOnNext xs (iop/action* do-fn)))

(defn ^Observable drop
  [n ^Observable xs]
  (.skip xs n))

(defn ^Observable drop-while
  [p ^Observable xs]
  (.skipWhile xs (fn->predicate p)))

(defn ^Observable every?
  "Returns an Observable that emits a single true value if (p x) is true for
  all x in xs. Otherwise emits false.

  See:
    clojure.core/every?
    rx.Observable/all
  "
  [p ^Observable xs]
  (.all xs (fn->predicate p)))

(defn ^Observable filter
  [p ^Observable xs]
  (.filter xs (fn->predicate p)))

(defn ^Observable first
  "Returns an Observable that emits the first item emitted by xs, or an
  empty Observable if xs is empty.

  See:
    rx.Observable/takeFirst
  "
  [^Observable xs]
  (.takeFirst xs))

; TODO group-by

(defn interpose
  [sep xs]
  (let [op (operator* (fn [o]
                        (let [first? (atom true)]
                          (subscriber o (fn [o v]
                                          (if-not (compare-and-set! first? true false)
                                            (on-next o sep))
                                          (on-next o v))))))]
    (lift op xs)))

(defn into
  "Returns an observable that emits a single value which is all of the
  values of from-observable conjoined onto to

  See:
    clojure.core/into
    rx.Observable/toList
  "
  [to ^Observable from-observable]
  (->> from-observable
   .toList
   (map (partial clojure.core/into to))))

(defn keep
  [f xs]
  (filter (complement nil?) (map f xs)))

(defn keep-indexed
  [f xs]
  (filter (complement nil?) (map-indexed f xs)))

(defn ^Observable map*
  "Map a function over an Observable of Observables.

  Each item from the first emitted Observable is the first arg, each
  item from the second emitted Observable is the second arg, and so on.

  See:
    map
    clojure.core/map
    rx.Observable/zip
  "
  [f ^Observable observable]
  (Observable/zip observable
                  ^rx.functions.FuncN (iop/fnN* f)))

(defn ^Observable map
  "Map a function over one or more observable sequences.

  Each item from the first Observable is the first arg, each item
  from the second Observable is the second arg, and so on.

  See:
    clojure.core/map
    rx.Observable/zip
  "
  [f & observables]
  (Observable/zip ^Iterable observables
                  ^rx.functions.FuncN (iop/fnN* f)))

(defn ^Observable mapcat
  "Returns an observable which, for each value x in xs, calls (f x), which must
  return an Observable. The resulting observables are concatentated together
  into one observable.

  See:
    clojure.core/mapcat
    rx.Observable/flatMap
  "
  ([f ^Observable xs] (.flatMap xs (iop/fn* f))))

(defn map-indexed
  "Returns an observable that invokes (f index value) for each value of the input
  observable. index starts at 0.

  See:
    clojure.core/map-indexed
  "
  [f xs]
  (let [op (operator* (fn [o]
                        (let [n (atom -1)]
                          (subscriber o
                                      (fn [o v]
                                        (on-next o (f (swap! n inc) v)))))))]
    (lift op xs)))

(def next
  "Returns an observable that emits all but the first element of the input observable.

  See:
    clojure.core/next
  "
  (partial drop 1))

(defn nth
  "Returns an Observable that emits the value at the index in the given
  Observable.  nth throws an IndexOutOfBoundsException unless not-found
  is supplied.

  Note that the Observable is the *first* arg!
  "
  ([^Observable xs index]
   (.elementAt xs index))
  ([^Observable xs index not-found]
   (.elementAtOrDefault xs index not-found)))

; TODO partition. Use window

(defn ^Observable reduce
  ([f ^Observable xs] (.reduce xs (iop/fn* f)))
  ([f val ^Observable xs] (.reduce xs val (iop/fn* f))))

(defn ^Observable reductions
  ([f ^Observable xs] (.scan xs (iop/fn* f)))
  ([f val ^Observable xs] (.scan xs val (iop/fn* f))))

(def rest
  "Same as rx/next"
  next)

(defn some
  "Returns an observable that emits the first logical true value of (pred x) for
  any x in xs, else completes immediately.

  See:
    clojure.core/some
  "
  [p ^Observable xs]
  (->> xs
       (map p)
       (filter identity)
       first))

(defn sorted-list
  "Returns an observable that emits a *single value* which is a sorted List
  of the items in coll, where the sort order is determined by comparing
  items.  If no comparator is supplied, uses compare. comparator must
  implement java.util.Comparator.

  Use sort if you don't want the sequence squashed down to a List.

  See:
    rx.Observable/toSortedList
    sort
  "
  ([coll] (sorted-list clojure.core/compare coll))
  ([comp ^Observable coll]
   (.toSortedList coll (iop/fn [a b]
                         ; force to int so rxjava doesn't have a fit
                         (int (comp a b))))))

(defn sorted-list-by
  "Returns an observable that emits a *single value* which is a sorted List
  of the items in coll, where the sort order is determined by comparing
  (keyfn item).  If no comparator is supplied, uses compare. comparator must
  implement java.util.Comparator.

  Use sort-by if you don't want the sequence squashed down to a List.

  See:
    rx.Observable/toSortedList
    sort-by
  "
  ([keyfn coll] (sorted-list-by keyfn clojure.core/compare coll))
  ([keyfn comp ^Observable coll]
   (.toSortedList coll (iop/fn [a b]
                         ; force to int so rxjava doesn't have a fit
                         (int (comp (keyfn a) (keyfn b)))))))

(defn sort
  "Returns an observable that emits the items in xs, where the sort order is
  determined by comparing items. If no comparator is supplied, uses compare.
  comparator must implement java.util.Comparator.

  See:
    sorted-list
    clojure.core/sort
  "
  ([xs]
   (->> xs
        (sorted-list)
        (mapcat seq->o)))
  ([comp xs]
   (->> xs
        (sorted-list comp)
        (mapcat seq->o))))

(defn sort-by
  "Returns an observable that emits the items in xs, where the sort order is
  determined by comparing (keyfn item). If no comparator is supplied, uses
  compare. comparator must implement java.util.Comparator.

  See:
    clojure.core/sort-by
  "
  ([keyfn xs]
   (->> (sorted-list-by keyfn xs)
        (mapcat seq->o)))
  ([keyfn comp ^Observable xs]
   (->> xs
        (sorted-list-by keyfn comp)
        (mapcat seq->o))))

(defn split-with
  "Returns an observable that emits a pair of observables

    [(take-while p xs) (drop-while p xs)]

  See:
    rx.lang.clojure/take-while
    rx.lang.clojure/drop-while
    clojure.core/split-with
  "
  [p xs]
  (return [(take-while p xs) (drop-while p xs)]))

(defn ^Observable take
  "Returns an observable that emits the first n elements of xs.

  See:
    clojure.core/take
  "
  [n ^Observable xs]
  {:pre [(>= n 0)]}
  (.take xs n))

(defn take-while
  "Returns an Observable that emits xs until the first x such that
  (p x) is falsey.

  See:
    clojure.core/take-while
    rx.Observable/takeWhile
  "
  [p ^Observable xs]
  (.takeWhile xs (fn->predicate p)))

;################################################################################;

(defn throw
  "Returns an Observable the simply emits the given exception with on-error

  See:
    rx.Observable/error
  "
  [^Throwable e]
  (Observable/error e))

(defn catch*
  "Returns an observable that, when Observable o triggers an error, e, continues with
  Observable returned by (apply f e args) if (p e) is true. If (p e) returns a Throwable
  that value is passed as e.

  If p is a class object, a normal instance? check is performed rather than calling it
  as a function. If the value returned by (p e) is not true, the error is propagated.

  Examples:

    (-> my-observable

        ; On IllegalArgumentException, just emit 1
        (catch* IllegalArgumentException (fn [e] (rx/return 1)))

        ; If exception message contains \"WAT\", emit [\\W \\A \\T]
        (catch* #(-> % .getMessage (.contains \"WAT\")) (rx/seq->o [\\W \\A \\T])))

  See:

    http://netflix.github.io/RxJava/javadoc/rx/Observable.html#onErrorResumeNext(rx.util.functions.Func1)
  "
  [^Observable o p f & args]
  (let [p (if (class? p)
            (fn [e] (.isInstance ^Class p e))
            p)]
    (.onErrorResumeNext o
                        ^Func1 (iop/fn [e]
                                 (if-let [maybe-e (p e)]
                                   (apply f (if (instance? Throwable maybe-e) maybe-e e) args)
                                   (rx.lang.clojure.core/throw e))))))

(defmacro catch
  "Macro version of catch*.

  The body of the catch is wrapped in an implicit (do). It must evaluate to an Observable.

  Note that the source observable is the first argument so this won't mix well with ->>
  threading.

  Example:

    (-> my-observable
        ; just emit 0 on IllegalArgumentException
        (catch IllegalArgumentException e
          (rx/return 0))

        (catch DependencyException e
          (if (.isMinor e)
            (rx/return 0)
            (rx/throw (WebException. 503)))))

  See:
    catch*
  "
  [o p binding & body]
  `(catch* ~o ~p (fn [~binding] ~@body)))

(defn finally*
  "Returns an Observable that, as a side-effect, executes (apply f args) when the given
  Observable completes regardless of success or failure.

  Example:

    (-> my-observable
        (finally* (fn [] (println \"Done\"))))

  "
  [^Observable o f & args]
  (.finallyDo o ^Action0 (iop/action [] (apply f args))))

(defmacro finally
  "Macro version of finally*.

  Example:

    (-> my-observable
        (finally (println \"Done\")))

  See:
    finally*
  "
  [o & body]
  `(finally* ~o (fn [] ~@body)))

;################################################################################;

(defn generator*
  "Creates an observable that calls (f observable & args) which should emit values
  with (rx/on-next observable value).

  Automatically calls on-completed on return, or on-error if any exception is thrown.

  f should exit early if (rx/unsubscribed? observable) returns true

  Examples:

    ; An observable that emits just 99
    (rx/generator* on-next 99)
  "
  [f & args]
  (observable* (-> #(apply f % args)
                   wrap-on-completed
                   wrap-on-error)))

(defmacro generator
  "Create an observable that executes body which should emit a sequence. bindings
  should be a single [observer] argument.

  Automatically calls on-completed on return, or on-error if any exception is thrown.

  The body should exit early if (rx/unsubscribed? observable) returns true

  Examples:

    ; make an observer that emits [0 1 2 3 4]
    (generator [observer]
      (dotimes [i 5]
        (on-next observer i)))

  "
  [bindings & body]
  `(generator* (fn ~bindings ~@body)))

;################################################################################;

; Import public graph symbols here. I want them in this namespace, but implementing
; them here with all the clojure.core symbols excluded is a pain.
(intern *ns* (with-meta 'let-o* (meta #'graph/let-o*)) @#'graph/let-o*)
(intern *ns* (with-meta 'let-o (meta #'graph/let-o)) @#'graph/let-o)

;################################################################################;

; Import some public realized symbols here. I want them in this namespace, but implementing
; them here with all the clojure.core symbols excluded is a pain.
(intern *ns* (with-meta 'let-realized (meta #'realized/let-realized)) @#'realized/let-realized)

