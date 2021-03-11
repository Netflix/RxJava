/*
 * Copyright (c) 2016-present, RxJava Contributors.
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

package io.reactivex.rxjava3.observable;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.*;
import io.reactivex.rxjava3.observables.GroupedObservable;
import io.reactivex.rxjava3.testsupport.TestObserverEx;

/**
 * Test super/extends of generics.
 *
 * See https://github.com/Netflix/RxJava/pull/331
 */
public class ObservableCovarianceTest extends RxJavaTest {

    /**
     * This won't compile if super/extends isn't done correctly on generics.
     */
    @Test
    public void covarianceOfFrom() {
        Observable.<Movie> just(new HorrorMovie());
        Observable.<Movie> fromIterable(new ArrayList<HorrorMovie>());
        // Observable.<HorrorMovie>from(new Movie()); // may not compile
    }

    @Test
    public void sortedList() {
        Comparator<Media> sortFunction = (t1, t2) -> 1;

        // this one would work without the covariance generics
        Observable<Media> o = Observable.just(new Movie(), new TVSeason(), new Album());
        o.toSortedList(sortFunction);

        // this one would NOT work without the covariance generics
        Observable<Movie> o2 = Observable.just(new Movie(), new ActionMovie(), new HorrorMovie());
        o2.toSortedList(sortFunction);
    }

    @Test
    public void groupByCompose() {
        Observable<Movie> movies = Observable.just(new HorrorMovie(), new ActionMovie(), new Movie());
        TestObserverEx<String> to = new TestObserverEx<>();
        movies
        .groupBy((Function<Movie, Object>) Movie::getClass)
        .doOnNext(g -> System.out.println(g.getKey()))
        .flatMap((Function<GroupedObservable<Object, Movie>, Observable<String>>) g -> g
        .doOnNext(System.out::println)
        .compose(m -> m.concatWith(Observable.just(new ActionMovie()))
        )
        .map(Object::toString))
        .subscribe(to);
        to.assertTerminated();
        to.assertNoErrors();
        //        System.out.println(ts.getOnNextEvents());
        assertEquals(6, to.values().size());
    }

    @SuppressWarnings("unused")
    @Test
    public void covarianceOfCompose() {
        Observable<HorrorMovie> movie = Observable.just(new HorrorMovie());
        Observable<Movie> movie2 = movie.compose(t -> Observable.just(new Movie()));
    }

    @SuppressWarnings("unused")
    @Test
    public void covarianceOfCompose2() {
        Observable<Movie> movie = Observable.just(new HorrorMovie());
        Observable<HorrorMovie> movie2 = movie.compose(t -> Observable.just(new HorrorMovie()));
    }

    @SuppressWarnings("unused")
    @Test
    public void covarianceOfCompose3() {
        Observable<Movie> movie = Observable.just(new HorrorMovie());
        Observable<HorrorMovie> movie2 = movie.compose(t -> Observable.just(new HorrorMovie()).map(v -> v)
        );
    }

    @SuppressWarnings("unused")
    @Test
    public void covarianceOfCompose4() {
        Observable<HorrorMovie> movie = Observable.just(new HorrorMovie());
        Observable<HorrorMovie> movie2 = movie.compose(t1 -> t1.map(v -> v));
    }

    @Test
    public void composeWithDeltaLogic() {
        List<Movie> list1 = Arrays.asList(new Movie(), new HorrorMovie(), new ActionMovie());
        List<Movie> list2 = Arrays.asList(new ActionMovie(), new Movie(), new HorrorMovie(), new ActionMovie());
        Observable<List<Movie>> movies = Observable.just(list1, list2);
        movies.compose(deltaTransformer);
    }

    static final Function<List<List<Movie>>, Observable<Movie>> calculateDelta = listOfLists -> {
        if (listOfLists.size() == 1) {
            return Observable.fromIterable(listOfLists.get(0));
        } else {
            // diff the two
            List<Movie> newList = listOfLists.get(1);
            List<Movie> oldList = new ArrayList<>(listOfLists.get(0));

            Set<Movie> delta = new LinkedHashSet<>(newList);
            // remove all that match in old
            delta.removeAll(oldList);

            // filter oldList to those that aren't in the newList
            oldList.removeAll(newList);

            // for all left in the oldList we'll create DROP events
            for (@SuppressWarnings("unused") Movie old : oldList) {
                delta.add(new Movie());
            }

            return Observable.fromIterable(delta);
        }
    };

    static final ObservableTransformer<List<Movie>, Movie> deltaTransformer = movieList -> movieList
        .startWithItem(new ArrayList<>())
        .buffer(2, 1)
        .skip(1)
        .flatMap(calculateDelta);

    /*
     * Most tests are moved into their applicable classes such as [Operator]Tests.java
     */

    static class Media {
    }

    static class Movie extends Media {
    }

    static class HorrorMovie extends Movie {
    }

    static class ActionMovie extends Movie {
    }

    static class Album extends Media {
    }

    static class TVSeason extends Media {
    }

    static class Rating {
    }

    static class CoolRating extends Rating {
    }

    static class Result {
    }

    static class ExtendedResult extends Result {
    }
}
