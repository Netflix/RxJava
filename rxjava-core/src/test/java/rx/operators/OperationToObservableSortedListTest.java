package rx.operators;

import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.Observer;
import rx.util.functions.Func2;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static rx.operators.OperationToObservableSortedList.toSortedList;

public class OperationToObservableSortedListTest {

  @Test
  public void testSortedList() {
    Observable<Integer> w = Observable.from(1, 3, 2, 5, 4);
    Observable<List<Integer>> observable = Observable.create(toSortedList(w));

    @SuppressWarnings("unchecked")
    Observer<List<Integer>> aObserver = mock(Observer.class);
    observable.subscribe(aObserver);
    verify(aObserver, times(1)).onNext(Arrays.asList(1, 2, 3, 4, 5));
    verify(aObserver, Mockito.never()).onError(any(Throwable.class));
    verify(aObserver, times(1)).onCompleted();
  }

  @Test
  public void testSortedListWithCustomFunction() {
    Observable<Integer> w = Observable.from(1, 3, 2, 5, 4);
    Observable<List<Integer>> observable = Observable.create(toSortedList(w, new Func2<Integer, Integer, Integer>() {

      @Override
      public Integer call(Integer t1, Integer t2) {
        return t2 - t1;
      }

    }));

    @SuppressWarnings("unchecked")
    Observer<List<Integer>> aObserver = mock(Observer.class);
    observable.subscribe(aObserver);
    verify(aObserver, times(1)).onNext(Arrays.asList(5, 4, 3, 2, 1));
    verify(aObserver, Mockito.never()).onError(any(Throwable.class));
    verify(aObserver, times(1)).onCompleted();
  }
}
