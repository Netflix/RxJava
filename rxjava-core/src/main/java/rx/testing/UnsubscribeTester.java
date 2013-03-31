package rx.testing;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.util.functions.Action1;
import rx.util.functions.Func0;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnsubscribeTester
{
    private boolean isDone = false;
    private Subscription subscription;

    public UnsubscribeTester() {}

    /**
     * Tests the unsubscription semantics of an observable.
     *
     * @param provider Function that when called provides an instance of the observable being tested
     * @param generateOnCompleted Causes an observer generated by @param provider to generate an onCompleted event. Null to not test onCompleted.
     * @param generateOnError  Causes an observer generated by @param provider to generate an onError event. Null to not test onError.
     * @param generateOnNext  Causes an observer generated by @param provider to generate an onNext event. Null to not test onNext.
     * @param <T> The type of object passed by the Observable
     */
    public static <T, O extends Observable<T>> void test(Func0<O> provider, Action1<? super O> generateOnCompleted, Action1<? super O> generateOnError, Action1<? super O> generateOnNext)
    {
        if (generateOnCompleted != null) {
            O observable = provider.call();
            UnsubscribeTester tester1 = createOnCompleted(observable);
            UnsubscribeTester tester2 = createOnCompleted(observable);
            generateOnCompleted.call(observable);
            tester1.assertPassed();
            tester2.assertPassed();
        }
        if (generateOnError != null) {
            O observable = provider.call();
            UnsubscribeTester tester1 = createOnError(observable);
            UnsubscribeTester tester2 = createOnError(observable);
            generateOnError.call(observable);
            tester1.assertPassed();
            tester2.assertPassed();
        }
        if (generateOnNext != null) {
            O observable = provider.call();
            UnsubscribeTester tester1 = createOnNext(observable);
            UnsubscribeTester tester2 = createOnNext(observable);
            generateOnNext.call(observable);
            tester1.assertPassed();
            tester2.assertPassed();
        }
    }

    private static <T> UnsubscribeTester createOnCompleted(Observable<T> observable)
    {
        final UnsubscribeTester test = new UnsubscribeTester();
        test.setSubscription(observable.subscribe(new Observer<T>()
        {
            @Override
            public void onCompleted()
            {
                test.doUnsubscribe("onCompleted");
            }

            @Override
            public void onError(Exception e)
            {
                test.gotEvent("onError");
            }

            @Override
            public void onNext(T args)
            {
                test.gotEvent("onNext");
            }
        }));
        return test;
    }

    private static <T> UnsubscribeTester createOnError(Observable<T> observable)
    {
        final UnsubscribeTester test = new UnsubscribeTester();
        test.setSubscription(observable.subscribe(new Observer<T>()
        {
            @Override
            public void onCompleted()
            {
                test.gotEvent("onCompleted");
            }

            @Override
            public void onError(Exception e)
            {
                test.doUnsubscribe("onError");
            }

            @Override
            public void onNext(T args)
            {
                test.gotEvent("onNext");
            }
        }));
        return test;
    }

    private static <T> UnsubscribeTester createOnNext(Observable<T> observable)
    {
        final UnsubscribeTester test = new UnsubscribeTester();
        test.setSubscription(observable.subscribe(new Observer<T>()
        {
            @Override
            public void onCompleted()
            {
                test.gotEvent("onCompleted");
            }

            @Override
            public void onError(Exception e)
            {
                test.gotEvent("onError");
            }

            @Override
            public void onNext(T args)
            {
                test.doUnsubscribe("onNext");
            }
        }));
        return test;
    }

    private void setSubscription(Subscription subscription)
    {
        this.subscription = subscription;
    }

    private void gotEvent(String event)
    {
        assertFalse("received " + event + " after unsubscribe", isDone);
    }

    private void doUnsubscribe(String event)
    {
        gotEvent(event);
        if (subscription != null) {
            isDone = true;
            subscription.unsubscribe();
        }
    }

    private void assertPassed()
    {
        assertTrue("expected notification was received", isDone);
    }
}
