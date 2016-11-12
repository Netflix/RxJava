//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kgalligan/devel-doppl/RxJava/src/test/java/rx/internal/operators/OperatorMergeMaxConcurrentTest.java
//

#include "CoTouchlabDoppelTestingPlatformUtils.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "RxInternalOperatorsOperatorMergeMaxConcurrentTest.h"
#include "RxInternalUtilPlatformDependent.h"
#include "RxObservable.h"
#include "RxObservablesBlockingObservable.h"
#include "RxObserver.h"
#include "RxObserversTestSubscriber.h"
#include "RxScheduler.h"
#include "RxSchedulersSchedulers.h"
#include "RxSubscriber.h"
#include "RxSubscription.h"
#include "java/lang/Integer.h"
#include "java/lang/Iterable.h"
#include "java/lang/Runnable.h"
#include "java/lang/System.h"
#include "java/lang/Thread.h"
#include "java/lang/annotation/Annotation.h"
#include "java/util/ArrayList.h"
#include "java/util/Arrays.h"
#include "java/util/HashSet.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/Set.h"
#include "java/util/concurrent/CountDownLatch.h"
#include "java/util/concurrent/TimeUnit.h"
#include "java/util/concurrent/atomic/AtomicInteger.h"
#include "org/junit/Assert.h"
#include "org/junit/Before.h"
#include "org/junit/Test.h"
#include "org/mockito/Answers.h"
#include "org/mockito/Mock.h"
#include "org/mockito/MockitoAnnotations.h"

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$0();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$1();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$2();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$3();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$4();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$5();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$6();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$7();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$8();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$9();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$10();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$11();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$12();

__attribute__((unused)) static IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$13();

@interface RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable : NSObject < RxObservable_OnSubscribe > {
 @public
  JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount_;
  jint maxConcurrent_;
  volatile_jboolean failed_;
}

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)subscriptionCount
                                                      withInt:(jint)maxConcurrent;

- (void)callWithId:(RxSubscriber *)t1;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable, subscriptionCount_, JavaUtilConcurrentAtomicAtomicInteger *)

__attribute__((unused)) static void RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *self, JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent);

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent) NS_RETURNS_RETAINED;

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable)

@interface RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 : NSObject < JavaLangRunnable > {
 @public
  RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *this$0_;
  RxSubscriber *val$t1_;
}

- (void)run;

- (instancetype)initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable:(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *)outer$
                                                                                     withRxSubscriber:(RxSubscriber *)capture$0;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1, this$0_, RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1, val$t1_, RxSubscriber *)

__attribute__((unused)) static void RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *self, RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0);

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0) NS_RETURNS_RETAINED;

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0);

@interface RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 : RxObserversTestSubscriber {
 @public
  JavaUtilConcurrentCountDownLatch *val$cdl_;
}

- (void)onStart;

- (void)onNextWithId:(JavaLangInteger *)t;

- (instancetype)initWithJavaUtilConcurrentCountDownLatch:(JavaUtilConcurrentCountDownLatch *)capture$0;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1, val$cdl_, JavaUtilConcurrentCountDownLatch *)

__attribute__((unused)) static void RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *self, JavaUtilConcurrentCountDownLatch *capture$0);

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(JavaUtilConcurrentCountDownLatch *capture$0) NS_RETURNS_RETAINED;

__attribute__((unused)) static RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(JavaUtilConcurrentCountDownLatch *capture$0);

@implementation RxInternalOperatorsOperatorMergeMaxConcurrentTest

- (void)before {
  OrgMockitoMockitoAnnotations_initMocksWithId_(self);
}

- (void)testWhenMaxConcurrentIsOne {
  for (jint i = 0; i < 100; i++) {
    @autoreleasepool {
      id<JavaUtilList> os = create_JavaUtilArrayList_init();
      [os addWithId:[((RxObservable *) nil_chk(RxObservable_justWithId_withId_withId_withId_withId_(@"one", @"two", @"three", @"four", @"five"))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_newThread()]];
      [os addWithId:[((RxObservable *) nil_chk(RxObservable_justWithId_withId_withId_withId_withId_(@"one", @"two", @"three", @"four", @"five"))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_newThread()]];
      [os addWithId:[((RxObservable *) nil_chk(RxObservable_justWithId_withId_withId_withId_withId_(@"one", @"two", @"three", @"four", @"five"))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_newThread()]];
      id<JavaUtilList> expected = JavaUtilArrays_asListWithNSObjectArray_([IOSObjectArray arrayWithObjects:(id[]){ @"one", @"two", @"three", @"four", @"five", @"one", @"two", @"three", @"four", @"five", @"one", @"two", @"three", @"four", @"five" } count:15 type:NSString_class_()]);
      id<JavaUtilIterator> iter = [((id<JavaLangIterable>) nil_chk([((RxObservablesBlockingObservable *) nil_chk([((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(os, 1))) toBlocking])) toIterable])) iterator];
      id<JavaUtilList> actual = create_JavaUtilArrayList_init();
      while ([((id<JavaUtilIterator>) nil_chk(iter)) hasNext]) {
        [actual addWithId:[iter next]];
      }
      OrgJunitAssert_assertEqualsWithId_withId_(expected, actual);
    }
  }
}

- (void)testMaxConcurrent {
  for (jint times = 0; times < 100; times++) {
    @autoreleasepool {
      jint observableCount = 100;
      jint maxConcurrent = 2 + (times % 10);
      JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount = create_JavaUtilConcurrentAtomicAtomicInteger_initWithInt_(0);
      id<JavaUtilList> os = create_JavaUtilArrayList_init();
      id<JavaUtilList> scos = create_JavaUtilArrayList_init();
      for (jint i = 0; i < observableCount; i++) {
        RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *sco = create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(subscriptionCount, maxConcurrent);
        [scos addWithId:sco];
        [os addWithId:RxObservable_createWithRxObservable_OnSubscribe_(sco)];
      }
      id<JavaUtilIterator> iter = [((id<JavaLangIterable>) nil_chk([((RxObservablesBlockingObservable *) nil_chk([((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(os, maxConcurrent))) toBlocking])) toIterable])) iterator];
      id<JavaUtilList> actual = create_JavaUtilArrayList_init();
      while ([((id<JavaUtilIterator>) nil_chk(iter)) hasNext]) {
        [actual addWithId:[iter next]];
      }
      OrgJunitAssert_assertEqualsWithLong_withLong_(5 * observableCount, [actual size]);
      for (RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable * __strong sco in scos) {
        OrgJunitAssert_assertFalseWithBoolean_(JreLoadVolatileBoolean(&((RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *) nil_chk(sco))->failed_));
      }
    }
  }
}

- (void)testMergeALotOfSourcesOneByOneSynchronously {
  jint n = 10000;
  id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(n);
  for (jint i = 0; i < n; i++) {
    [sourceList addWithId:RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(i))];
  }
  id<JavaUtilIterator> it = [((RxObservablesBlockingObservable *) nil_chk([((RxObservable *) nil_chk(RxObservable_mergeWithRxObservable_withInt_(RxObservable_fromWithJavaLangIterable_(sourceList), 1))) toBlocking])) getIterator];
  jint j = 0;
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    OrgJunitAssert_assertEqualsWithId_withId_(JavaLangInteger_valueOfWithInt_(j), [it next]);
    j++;
  }
  OrgJunitAssert_assertEqualsWithLong_withLong_(j, n);
}

- (void)testMergeALotOfSourcesOneByOneSynchronouslyTakeHalf {
  jint n = 10000;
  id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(n);
  for (jint i = 0; i < n; i++) {
    [sourceList addWithId:RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(i))];
  }
  id<JavaUtilIterator> it = [((RxObservablesBlockingObservable *) nil_chk([((RxObservable *) nil_chk([((RxObservable *) nil_chk(RxObservable_mergeWithRxObservable_withInt_(RxObservable_fromWithJavaLangIterable_(sourceList), 1))) takeWithInt:n / 2])) toBlocking])) getIterator];
  jint j = 0;
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    OrgJunitAssert_assertEqualsWithId_withId_(JavaLangInteger_valueOfWithInt_(j), [it next]);
    j++;
  }
  OrgJunitAssert_assertEqualsWithLong_withLong_(j, n / 2);
}

- (void)testSimple {
  for (jint i = 1; i < 100; i++) {
    @autoreleasepool {
      RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
      id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(i);
      id<JavaUtilList> result = create_JavaUtilArrayList_initWithInt_(i);
      for (jint j = 1; j <= i; j++) {
        [sourceList addWithId:RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(j))];
        [result addWithId:JavaLangInteger_valueOfWithInt_(j)];
      }
      [((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, i))) subscribeWithRxSubscriber:ts];
      [ts assertNoErrors];
      [ts assertTerminalEvent];
      [ts assertReceivedOnNextWithJavaUtilList:result];
    }
  }
}

- (void)testSimpleOneLess {
  for (jint i = 2; i < 100; i++) {
    @autoreleasepool {
      RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
      id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(i);
      id<JavaUtilList> result = create_JavaUtilArrayList_initWithInt_(i);
      for (jint j = 1; j <= i; j++) {
        [sourceList addWithId:RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(j))];
        [result addWithId:JavaLangInteger_valueOfWithInt_(j)];
      }
      [((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, i - 1))) subscribeWithRxSubscriber:ts];
      [ts assertNoErrors];
      [ts assertTerminalEvent];
      [ts assertReceivedOnNextWithJavaUtilList:result];
    }
  }
}

- (void)testSimpleAsyncLoop {
  for (jint i = 0; i < 200; i++) {
    @autoreleasepool {
      [self testSimpleAsync];
    }
  }
}

- (void)testSimpleAsync {
  for (jint i = 1; i < 50; i++) {
    @autoreleasepool {
      RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
      id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(i);
      id<JavaUtilSet> expected = create_JavaUtilHashSet_initWithInt_(i);
      for (jint j = 1; j <= i; j++) {
        [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(j)))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
        [expected addWithId:JavaLangInteger_valueOfWithInt_(j)];
      }
      [((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, i))) subscribeWithRxSubscriber:ts];
      [ts awaitTerminalEventWithLong:1 withJavaUtilConcurrentTimeUnit:JreLoadEnum(JavaUtilConcurrentTimeUnit, SECONDS)];
      [ts assertNoErrors];
      id<JavaUtilSet> actual = create_JavaUtilHashSet_initWithJavaUtilCollection_([ts getOnNextEvents]);
      OrgJunitAssert_assertEqualsWithId_withId_(expected, actual);
    }
  }
}

- (void)testSimpleOneLessAsyncLoop {
  jint max = 200;
  if (RxInternalUtilPlatformDependent_isAndroid() || CoTouchlabDoppelTestingPlatformUtils_isJ2objc()) {
    max = 50;
  }
  for (jint i = 0; i < max; i++) {
    @autoreleasepool {
      [self testSimpleOneLessAsync];
    }
  }
}

- (void)testSimpleOneLessAsync {
  jlong t = JavaLangSystem_currentTimeMillis();
  for (jint i = 2; i < 50; i++) {
    @autoreleasepool {
      if (JavaLangSystem_currentTimeMillis() - t > [((JavaUtilConcurrentTimeUnit *) nil_chk(JreLoadEnum(JavaUtilConcurrentTimeUnit, SECONDS))) toMillisWithLong:9]) {
        break;
      }
      RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
      id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(i);
      id<JavaUtilSet> expected = create_JavaUtilHashSet_initWithInt_(i);
      for (jint j = 1; j <= i; j++) {
        [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_justWithId_(JavaLangInteger_valueOfWithInt_(j)))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
        [expected addWithId:JavaLangInteger_valueOfWithInt_(j)];
      }
      [((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, i - 1))) subscribeWithRxSubscriber:ts];
      [ts awaitTerminalEventWithLong:1 withJavaUtilConcurrentTimeUnit:JreLoadEnum(JavaUtilConcurrentTimeUnit, SECONDS)];
      [ts assertNoErrors];
      id<JavaUtilSet> actual = create_JavaUtilHashSet_initWithJavaUtilCollection_([ts getOnNextEvents]);
      OrgJunitAssert_assertEqualsWithId_withId_(expected, actual);
    }
  }
}

- (void)testBackpressureHonored {
  id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(3);
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  JavaUtilConcurrentCountDownLatch *cdl = create_JavaUtilConcurrentCountDownLatch_initWithInt_(5);
  RxObserversTestSubscriber *ts = create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(cdl);
  [((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, 2))) subscribeWithRxSubscriber:ts];
  [ts requestMoreWithLong:5];
  [cdl await];
  [ts assertNoErrors];
  OrgJunitAssert_assertEqualsWithLong_withLong_(5, [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size]);
  OrgJunitAssert_assertEqualsWithLong_withLong_(0, [ts getCompletions]);
  [ts unsubscribe];
}

- (void)testTake {
  id<JavaUtilList> sourceList = create_JavaUtilArrayList_initWithInt_(3);
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  [sourceList addWithId:[((RxObservable *) nil_chk(RxObservable_rangeWithInt_withInt_(0, 100000))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_io()]];
  RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
  [((RxObservable *) nil_chk([((RxObservable *) nil_chk(RxObservable_mergeWithJavaLangIterable_withInt_(sourceList, 2))) takeWithInt:5])) subscribeWithRxSubscriber:ts];
  [ts awaitTerminalEvent];
  [ts assertNoErrors];
  OrgJunitAssert_assertEqualsWithLong_withLong_(5, [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size]);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  RxInternalOperatorsOperatorMergeMaxConcurrentTest_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

- (void)dealloc {
  RELEASE_(stringObserver_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, -1, -1, -1, -1, 0, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 1, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 2, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 3, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 4, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 5, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 6, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 7, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 8, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 9, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 10, -1 },
    { NULL, "V", 0x1, -1, -1, 11, -1, 12, -1 },
    { NULL, "V", 0x1, -1, -1, 11, -1, 13, -1 },
    { NULL, NULL, 0x1, -1, -1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(before);
  methods[1].selector = @selector(testWhenMaxConcurrentIsOne);
  methods[2].selector = @selector(testMaxConcurrent);
  methods[3].selector = @selector(testMergeALotOfSourcesOneByOneSynchronously);
  methods[4].selector = @selector(testMergeALotOfSourcesOneByOneSynchronouslyTakeHalf);
  methods[5].selector = @selector(testSimple);
  methods[6].selector = @selector(testSimpleOneLess);
  methods[7].selector = @selector(testSimpleAsyncLoop);
  methods[8].selector = @selector(testSimpleAsync);
  methods[9].selector = @selector(testSimpleOneLessAsyncLoop);
  methods[10].selector = @selector(testSimpleOneLessAsync);
  methods[11].selector = @selector(testBackpressureHonored);
  methods[12].selector = @selector(testTake);
  methods[13].selector = @selector(init);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "stringObserver_", "LRxObserver;", .constantValue.asLong = 0, 0x0, -1, -1, 14, 15 },
  };
  static const void *ptrTable[] = { (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$0, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$1, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$2, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$3, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$4, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$5, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$6, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$7, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$8, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$9, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$10, "LJavaLangException;", (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$11, (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$12, "Lrx/Observer<Ljava/lang/String;>;", (void *)&RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$13, "LRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable;" };
  static const J2ObjcClassInfo _RxInternalOperatorsOperatorMergeMaxConcurrentTest = { "OperatorMergeMaxConcurrentTest", "rx.internal.operators", ptrTable, methods, fields, 7, 0x1, 14, 1, -1, 16, -1, -1, -1 };
  return &_RxInternalOperatorsOperatorMergeMaxConcurrentTest;
}

@end

void RxInternalOperatorsOperatorMergeMaxConcurrentTest_init(RxInternalOperatorsOperatorMergeMaxConcurrentTest *self) {
  NSObject_init(self);
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_init() {
  J2OBJC_NEW_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest, init)
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_init() {
  J2OBJC_CREATE_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest, init)
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$0() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitBefore() } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$1() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$2() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$3() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$4() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$5() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$6() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$7() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 30000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$8() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 10000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$9() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 20000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$10() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 10000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$11() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 5000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$12() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 5000) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxInternalOperatorsOperatorMergeMaxConcurrentTest__Annotations$13() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgMockitoMock(JreLoadEnum(OrgMockitoAnswers, RETURNS_DEFAULTS), [IOSObjectArray arrayWithObjects:(id[]){  } count:0 type:NSObject_class_()], @"") } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(RxInternalOperatorsOperatorMergeMaxConcurrentTest)

@implementation RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)subscriptionCount
                                                      withInt:(jint)maxConcurrent {
  RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(self, subscriptionCount, maxConcurrent);
  return self;
}

- (void)callWithId:(RxSubscriber *)t1 {
  [create_JavaLangThread_initWithJavaLangRunnable_(create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(self, t1)) start];
}

- (void)dealloc {
  RELEASE_(subscriptionCount_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, NULL, 0x0, -1, 0, -1, -1, -1, -1 },
    { NULL, "V", 0x1, 1, 2, -1, 3, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(initWithJavaUtilConcurrentAtomicAtomicInteger:withInt:);
  methods[1].selector = @selector(callWithId:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "subscriptionCount_", "LJavaUtilConcurrentAtomicAtomicInteger;", .constantValue.asLong = 0, 0x12, -1, -1, -1, -1 },
    { "maxConcurrent_", "I", .constantValue.asLong = 0, 0x12, -1, -1, -1, -1 },
    { "failed_", "Z", .constantValue.asLong = 0, 0x40, -1, -1, -1, -1 },
  };
  static const void *ptrTable[] = { "LJavaUtilConcurrentAtomicAtomicInteger;I", "call", "LRxSubscriber;", "(Lrx/Subscriber<-Ljava/lang/String;>;)V", "LRxInternalOperatorsOperatorMergeMaxConcurrentTest;", "Ljava/lang/Object;Lrx/Observable$OnSubscribe<Ljava/lang/String;>;" };
  static const J2ObjcClassInfo _RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable = { "SubscriptionCheckObservable", "rx.internal.operators", ptrTable, methods, fields, 7, 0xa, 2, 3, 4, -1, -1, 5, -1 };
  return &_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable;
}

@end

void RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *self, JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent) {
  NSObject_init(self);
  JreAssignVolatileBoolean(&self->failed_, false);
  JreStrongAssign(&self->subscriptionCount_, subscriptionCount);
  self->maxConcurrent_ = maxConcurrent;
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent) {
  J2OBJC_NEW_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable, initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_, subscriptionCount, maxConcurrent)
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_(JavaUtilConcurrentAtomicAtomicInteger *subscriptionCount, jint maxConcurrent) {
  J2OBJC_CREATE_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable, initWithJavaUtilConcurrentAtomicAtomicInteger_withInt_, subscriptionCount, maxConcurrent)
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable)

@implementation RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1

- (void)run {
  if ([((JavaUtilConcurrentAtomicAtomicInteger *) nil_chk(this$0_->subscriptionCount_)) incrementAndGet] > this$0_->maxConcurrent_) {
    JreAssignVolatileBoolean(&this$0_->failed_, true);
  }
  [((RxSubscriber *) nil_chk(val$t1_)) onNextWithId:@"one"];
  [val$t1_ onNextWithId:@"two"];
  [val$t1_ onNextWithId:@"three"];
  [val$t1_ onNextWithId:@"four"];
  [val$t1_ onNextWithId:@"five"];
  [this$0_->subscriptionCount_ decrementAndGet];
  [val$t1_ onCompleted];
}

- (instancetype)initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable:(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *)outer$
                                                                                     withRxSubscriber:(RxSubscriber *)capture$0 {
  RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(self, outer$, capture$0);
  return self;
}

- (void)dealloc {
  RELEASE_(this$0_);
  RELEASE_(val$t1_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, -1, -1, -1, -1, -1, -1 },
    { NULL, NULL, 0x0, -1, 0, -1, 1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(run);
  methods[1].selector = @selector(initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable:withRxSubscriber:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "this$0_", "LRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable;", .constantValue.asLong = 0, 0x1012, -1, -1, -1, -1 },
    { "val$t1_", "LRxSubscriber;", .constantValue.asLong = 0, 0x1012, -1, -1, 2, -1 },
  };
  static const void *ptrTable[] = { "LRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable;LRxSubscriber;", "(Lrx/internal/operators/OperatorMergeMaxConcurrentTest$SubscriptionCheckObservable;Lrx/Subscriber<-Ljava/lang/String;>;)V", "Lrx/Subscriber<-Ljava/lang/String;>;", "LRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable;", "callWithId:" };
  static const J2ObjcClassInfo _RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 = { "", "rx.internal.operators", ptrTable, methods, fields, 7, 0x8008, 2, 2, 3, -1, 4, -1, -1 };
  return &_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1;
}

@end

void RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *self, RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0) {
  JreStrongAssign(&self->this$0_, outer$);
  JreStrongAssign(&self->val$t1_, capture$0);
  NSObject_init(self);
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0) {
  J2OBJC_NEW_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1, initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_, outer$, capture$0)
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1 *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1_initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable *outer$, RxSubscriber *capture$0) {
  J2OBJC_CREATE_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_$1, initWithRxInternalOperatorsOperatorMergeMaxConcurrentTest_SubscriptionCheckObservable_withRxSubscriber_, outer$, capture$0)
}

@implementation RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1

- (void)onStart {
  [self requestWithLong:0];
}

- (void)onNextWithId:(JavaLangInteger *)t {
  [super onNextWithId:t];
  [((JavaUtilConcurrentCountDownLatch *) nil_chk(val$cdl_)) countDown];
}

- (instancetype)initWithJavaUtilConcurrentCountDownLatch:(JavaUtilConcurrentCountDownLatch *)capture$0 {
  RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(self, capture$0);
  return self;
}

- (void)dealloc {
  JreCheckFinalize(self, [RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 class]);
  RELEASE_(val$cdl_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, -1, -1, -1, -1, -1, -1 },
    { NULL, "V", 0x1, 0, 1, -1, 2, -1, -1 },
    { NULL, NULL, 0x0, -1, 3, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(onStart);
  methods[1].selector = @selector(onNextWithId:);
  methods[2].selector = @selector(initWithJavaUtilConcurrentCountDownLatch:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "val$cdl_", "LJavaUtilConcurrentCountDownLatch;", .constantValue.asLong = 0, 0x1012, -1, -1, -1, -1 },
  };
  static const void *ptrTable[] = { "onNext", "LJavaLangInteger;", "(Ljava/lang/Integer;)V", "LJavaUtilConcurrentCountDownLatch;", "LRxInternalOperatorsOperatorMergeMaxConcurrentTest;", "testBackpressureHonored", "Lrx/observers/TestSubscriber<Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 = { "", "rx.internal.operators", ptrTable, methods, fields, 7, 0x8008, 3, 1, 4, -1, 5, 6, -1 };
  return &_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1;
}

@end

void RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *self, JavaUtilConcurrentCountDownLatch *capture$0) {
  JreStrongAssign(&self->val$cdl_, capture$0);
  RxObserversTestSubscriber_init(self);
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *new_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(JavaUtilConcurrentCountDownLatch *capture$0) {
  J2OBJC_NEW_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1, initWithJavaUtilConcurrentCountDownLatch_, capture$0)
}

RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1 *create_RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1_initWithJavaUtilConcurrentCountDownLatch_(JavaUtilConcurrentCountDownLatch *capture$0) {
  J2OBJC_CREATE_IMPL(RxInternalOperatorsOperatorMergeMaxConcurrentTest_$1, initWithJavaUtilConcurrentCountDownLatch_, capture$0)
}
