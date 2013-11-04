package rx.operators;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Observer;
import rx.concurrency.Schedulers;
import rx.util.functions.Func1;
import rx.util.functions.Func2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static rx.operators.OperationMap.*;

public class OperationMapTest {

  @Mock
  Observer<String> stringObserver;
  @Mock
  Observer<String> stringObserver2;

  final static Func2<String, Integer, String> APPEND_INDEX = new Func2<String, Integer, String>() {
    @Override
    public String call(String value, Integer index) {
      return value + index;
    }
  };

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testMap() {
    Map<String, String> m1 = getMap("One");
    Map<String, String> m2 = getMap("Two");
    Observable<Map<String, String>> observable = Observable.from(m1, m2);

    Observable<String> m = Observable.create(map(observable, new Func1<Map<String, String>, String>() {

      @Override
      public String call(Map<String, String> map) {
        return map.get("firstName");
      }

    }));
    m.subscribe(stringObserver);

    verify(stringObserver, never()).onError(any(Throwable.class));
    verify(stringObserver, times(1)).onNext("OneFirst");
    verify(stringObserver, times(1)).onNext("TwoFirst");
    verify(stringObserver, times(1)).onCompleted();
  }

  @Test
  public void testMapWithIndex() {
    Observable<String> w = Observable.from("a", "b", "c");
    Observable<String> m = Observable.create(mapWithIndex(w, APPEND_INDEX));
    m.subscribe(stringObserver);
    InOrder inOrder = inOrder(stringObserver);
    inOrder.verify(stringObserver, times(1)).onNext("a0");
    inOrder.verify(stringObserver, times(1)).onNext("b1");
    inOrder.verify(stringObserver, times(1)).onNext("c2");
    inOrder.verify(stringObserver, times(1)).onCompleted();
    verify(stringObserver, never()).onError(any(Throwable.class));
  }

  @Test
  public void testMapWithIndexAndMultipleSubscribers() {
    Observable<String> w = Observable.from("a", "b", "c");
    Observable<String> m = Observable.create(mapWithIndex(w, APPEND_INDEX));
    m.subscribe(stringObserver);
    m.subscribe(stringObserver2);
    InOrder inOrder = inOrder(stringObserver);
    inOrder.verify(stringObserver, times(1)).onNext("a0");
    inOrder.verify(stringObserver, times(1)).onNext("b1");
    inOrder.verify(stringObserver, times(1)).onNext("c2");
    inOrder.verify(stringObserver, times(1)).onCompleted();
    verify(stringObserver, never()).onError(any(Throwable.class));

    InOrder inOrder2 = inOrder(stringObserver2);
    inOrder2.verify(stringObserver2, times(1)).onNext("a0");
    inOrder2.verify(stringObserver2, times(1)).onNext("b1");
    inOrder2.verify(stringObserver2, times(1)).onNext("c2");
    inOrder2.verify(stringObserver2, times(1)).onCompleted();
    verify(stringObserver2, never()).onError(any(Throwable.class));
  }

  @Test
  public void testMapMany() {
            /* simulate a top-level async call which returns IDs */
    Observable<Integer> ids = Observable.from(1, 2);

            /* now simulate the behavior to take those IDs and perform nested async calls based on them */
    Observable<String> m = Observable.create(mapMany(ids, new Func1<Integer, Observable<String>>() {

      @Override
      public Observable<String> call(Integer id) {
                    /* simulate making a nested async call which creates another Observable */
        Observable<Map<String, String>> subObservable = null;
        if (id == 1) {
          Map<String, String> m1 = getMap("One");
          Map<String, String> m2 = getMap("Two");
          subObservable = Observable.from(m1, m2);
        } else {
          Map<String, String> m3 = getMap("Three");
          Map<String, String> m4 = getMap("Four");
          subObservable = Observable.from(m3, m4);
        }

                    /* simulate kicking off the async call and performing a select on it to transform the data */
        return Observable.create(map(subObservable, new Func1<Map<String, String>, String>() {
          @Override
          public String call(Map<String, String> map) {
            return map.get("firstName");
          }
        }));
      }

    }));
    m.subscribe(stringObserver);

    verify(stringObserver, never()).onError(any(Throwable.class));
    verify(stringObserver, times(1)).onNext("OneFirst");
    verify(stringObserver, times(1)).onNext("TwoFirst");
    verify(stringObserver, times(1)).onNext("ThreeFirst");
    verify(stringObserver, times(1)).onNext("FourFirst");
    verify(stringObserver, times(1)).onCompleted();
  }

  @Test
  public void testMapMany2() {
    Map<String, String> m1 = getMap("One");
    Map<String, String> m2 = getMap("Two");
    Observable<Map<String, String>> observable1 = Observable.from(m1, m2);

    Map<String, String> m3 = getMap("Three");
    Map<String, String> m4 = getMap("Four");
    Observable<Map<String, String>> observable2 = Observable.from(m3, m4);

    Observable<Observable<Map<String, String>>> observable = Observable.from(observable1, observable2);

    Observable<String> m = Observable.create(mapMany(observable, new Func1<Observable<Map<String, String>>, Observable<String>>() {

      @Override
      public Observable<String> call(Observable<Map<String, String>> o) {
        return Observable.create(map(o, new Func1<Map<String, String>, String>() {

          @Override
          public String call(Map<String, String> map) {
            return map.get("firstName");
          }
        }));
      }

    }));
    m.subscribe(stringObserver);

    verify(stringObserver, never()).onError(any(Throwable.class));
    verify(stringObserver, times(1)).onNext("OneFirst");
    verify(stringObserver, times(1)).onNext("TwoFirst");
    verify(stringObserver, times(1)).onNext("ThreeFirst");
    verify(stringObserver, times(1)).onNext("FourFirst");
    verify(stringObserver, times(1)).onCompleted();

  }

  @Test
  public void testMapWithError() {
    Observable<String> w = Observable.from("one", "fail", "two", "three", "fail");
    Observable<String> m = Observable.create(map(w, new Func1<String, String>() {
      @Override
      public String call(String s) {
        if ("fail".equals(s)) {
          throw new RuntimeException("Forced Failure");
        }
        return s;
      }
    }));

    m.subscribe(stringObserver);
    verify(stringObserver, times(1)).onNext("one");
    verify(stringObserver, never()).onNext("two");
    verify(stringObserver, never()).onNext("three");
    verify(stringObserver, never()).onCompleted();
    verify(stringObserver, times(1)).onError(any(Throwable.class));
  }

  @Test
  public void testMapWithSynchronousObservableContainingError() {
    Observable<String> w = Observable.from("one", "fail", "two", "three", "fail");
    final AtomicInteger c1 = new AtomicInteger();
    final AtomicInteger c2 = new AtomicInteger();
    Observable<String> m = Observable.create(map(w, new Func1<String, String>() {
      @Override
      public String call(String s) {
        if ("fail".equals(s))
          throw new RuntimeException("Forced Failure");
        System.out.println("BadMapper:" + s);
        c1.incrementAndGet();
        return s;
      }
    })).map(new Func1<String, String>() {
      @Override
      public String call(String s) {
        System.out.println("SecondMapper:" + s);
        c2.incrementAndGet();
        return s;
      }
    });

    m.subscribe(stringObserver);

    verify(stringObserver, times(1)).onNext("one");
    verify(stringObserver, never()).onNext("two");
    verify(stringObserver, never()).onNext("three");
    verify(stringObserver, never()).onCompleted();
    verify(stringObserver, times(1)).onError(any(Throwable.class));

    // we should have only returned 1 value: "one"
    assertEquals(1, c1.get());
    assertEquals(1, c2.get());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMapWithIssue417() {
    Observable.from(1).observeOn(Schedulers.threadPoolForComputation())
        .map(new Func1<Integer, Integer>() {
          public Integer call(Integer arg0) {
            throw new IllegalArgumentException("any error");
          }
        }).toBlockingObservable().single();
  }

  @Test
  public void testMapWithErrorInFuncAndThreadPoolScheduler() throws InterruptedException {
    // The error will throw in one of threads in the thread pool.
    // If map does not handle it, the error will disappear.
    // so map needs to handle the error by itself.
    final CountDownLatch latch = new CountDownLatch(1);
    Observable<String> m = Observable.from("one")
        .observeOn(Schedulers.threadPoolForComputation())
        .map(new Func1<String, String>() {
          public String call(String arg0) {
            try {
              throw new IllegalArgumentException("any error");
            } finally {
              latch.countDown();
            }
          }
        });

    m.subscribe(stringObserver);
    latch.await();
    InOrder inorder = inOrder(stringObserver);
    inorder.verify(stringObserver, times(1)).onError(any(IllegalArgumentException.class));
    inorder.verifyNoMoreInteractions();
  }

  private static Map<String, String> getMap(String prefix) {
    Map<String, String> m = new HashMap<String, String>();
    m.put("firstName", prefix + "First");
    m.put("lastName", prefix + "Last");
    return m;
  }
}
