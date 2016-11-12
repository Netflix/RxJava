//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kgalligan/devel-doppl/RxJava/src/test/java/rx/BackpressureTests.java
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "RxBackpressureTests.h"
#include "RxFunctionsFunc1.h"
#include "RxFunctionsFunc2.h"
#include "RxInternalUtilRxRingBuffer.h"
#include "RxObservable.h"
#include "RxObserversTestSubscriber.h"
#include "RxScheduler.h"
#include "RxSchedulersSchedulers.h"
#include "RxSubscriber.h"
#include "RxSubscription.h"
#include "RxTestTestObstructionDetection.h"
#include "com/google/j2objc/WeakProxy.h"
#include "java/io/PrintStream.h"
#include "java/lang/Integer.h"
#include "java/lang/System.h"
#include "java/lang/Thread.h"
#include "java/lang/annotation/Annotation.h"
#include "java/util/List.h"
#include "java/util/concurrent/ConcurrentLinkedQueue.h"
#include "java/util/concurrent/atomic/AtomicInteger.h"
#include "java/util/concurrent/atomic/AtomicLong.h"
#include "org/junit/After.h"
#include "org/junit/Assert.h"
#include "org/junit/Rule.h"
#include "org/junit/Test.h"
#include "org/junit/rules/TestName.h"

@interface RxBackpressureTests ()

+ (RxObservable *)incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter;

+ (RxObservable *)incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter
                                    withJavaUtilConcurrentConcurrentLinkedQueue:(JavaUtilConcurrentConcurrentLinkedQueue *)threadsSeen;

+ (RxObservable *)firehoseWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter;

@end

__attribute__((unused)) static RxObservable *RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *counter);

__attribute__((unused)) static RxObservable *RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *counter, JavaUtilConcurrentConcurrentLinkedQueue *threadsSeen);

__attribute__((unused)) static RxObservable *RxBackpressureTests_firehoseWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *counter);

__attribute__((unused)) static IOSObjectArray *RxBackpressureTests__Annotations$0();

__attribute__((unused)) static IOSObjectArray *RxBackpressureTests__Annotations$1();

__attribute__((unused)) static IOSObjectArray *RxBackpressureTests__Annotations$2();

__attribute__((unused)) static IOSObjectArray *RxBackpressureTests__Annotations$3();

@interface RxBackpressureTests_$2 : NSObject < RxFunctionsFunc2 >

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1
                         withId:(JavaLangInteger *)t2;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(RxBackpressureTests_$2)

__attribute__((unused)) static void RxBackpressureTests_$2_init(RxBackpressureTests_$2 *self);

__attribute__((unused)) static RxBackpressureTests_$2 *new_RxBackpressureTests_$2_init() NS_RETURNS_RETAINED;

__attribute__((unused)) static RxBackpressureTests_$2 *create_RxBackpressureTests_$2_init();

@interface RxBackpressureTests_$3 : NSObject < RxFunctionsFunc2 >

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1
                         withId:(JavaLangInteger *)t2;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(RxBackpressureTests_$3)

__attribute__((unused)) static void RxBackpressureTests_$3_init(RxBackpressureTests_$3 *self);

__attribute__((unused)) static RxBackpressureTests_$3 *new_RxBackpressureTests_$3_init() NS_RETURNS_RETAINED;

__attribute__((unused)) static RxBackpressureTests_$3 *create_RxBackpressureTests_$3_init();

@interface RxBackpressureTests_$4 : NSObject < RxObservable_OnSubscribe > {
 @public
  JavaUtilConcurrentAtomicAtomicLong *requested_;
  JavaUtilConcurrentAtomicAtomicInteger *val$counter_;
  JavaUtilConcurrentConcurrentLinkedQueue *val$threadsSeen_;
}

- (void)callWithId:(RxSubscriber *)s;

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)capture$0
                  withJavaUtilConcurrentConcurrentLinkedQueue:(JavaUtilConcurrentConcurrentLinkedQueue *)capture$1;

@end

J2OBJC_EMPTY_STATIC_INIT(RxBackpressureTests_$4)

J2OBJC_FIELD_SETTER(RxBackpressureTests_$4, requested_, JavaUtilConcurrentAtomicAtomicLong *)
J2OBJC_FIELD_SETTER(RxBackpressureTests_$4, val$counter_, JavaUtilConcurrentAtomicAtomicInteger *)
J2OBJC_FIELD_SETTER(RxBackpressureTests_$4, val$threadsSeen_, JavaUtilConcurrentConcurrentLinkedQueue *)

__attribute__((unused)) static void RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(RxBackpressureTests_$4 *self, JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1);

__attribute__((unused)) static RxBackpressureTests_$4 *new_RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1) NS_RETURNS_RETAINED;

__attribute__((unused)) static RxBackpressureTests_$4 *create_RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1);

@interface RxBackpressureTests_$5 : NSObject < RxObservable_OnSubscribe > {
 @public
  jint i_;
  JavaUtilConcurrentAtomicAtomicInteger *val$counter_;
}

- (void)callWithId:(RxSubscriber *)s;

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)capture$0;

@end

J2OBJC_EMPTY_STATIC_INIT(RxBackpressureTests_$5)

J2OBJC_FIELD_SETTER(RxBackpressureTests_$5, val$counter_, JavaUtilConcurrentAtomicAtomicInteger *)

__attribute__((unused)) static void RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(RxBackpressureTests_$5 *self, JavaUtilConcurrentAtomicAtomicInteger *capture$0);

__attribute__((unused)) static RxBackpressureTests_$5 *new_RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *capture$0) NS_RETURNS_RETAINED;

__attribute__((unused)) static RxBackpressureTests_$5 *create_RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *capture$0);

@interface RxBackpressureTests_$1 : NSObject < RxFunctionsFunc1 > {
 @public
  volatile_jint sink_;
}

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(RxBackpressureTests_$1)

__attribute__((unused)) static void RxBackpressureTests_$1_init(RxBackpressureTests_$1 *self);

__attribute__((unused)) static RxBackpressureTests_$1 *new_RxBackpressureTests_$1_init() NS_RETURNS_RETAINED;

__attribute__((unused)) static RxBackpressureTests_$1 *create_RxBackpressureTests_$1_init();

J2OBJC_INITIALIZED_DEFN(RxBackpressureTests)

id<RxFunctionsFunc1> RxBackpressureTests_SLOW_PASS_THRU;

@implementation RxBackpressureTests

- (void)doAfterTest {
  RxTestTestObstructionDetection_checkObstruction();
}

- (void)testZipSync {
  jint NUM = JreFpToInt((JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 4.1));
  JavaUtilConcurrentAtomicAtomicInteger *c1 = create_JavaUtilConcurrentAtomicAtomicInteger_init();
  JavaUtilConcurrentAtomicAtomicInteger *c2 = create_JavaUtilConcurrentAtomicAtomicInteger_init();
  RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
  RxObservable *zipped = RxObservable_zipWithRxObservable_withRxObservable_withRxFunctionsFunc2_(RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(c1), RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(c2), create_RxBackpressureTests_$2_init());
  [((RxObservable *) nil_chk([((RxObservable *) nil_chk(zipped)) takeWithInt:NUM])) subscribeWithRxSubscriber:ts];
  [ts awaitTerminalEvent];
  [ts assertNoErrors];
  [((JavaIoPrintStream *) nil_chk(JreLoadStatic(JavaLangSystem, out))) printlnWithNSString:JreStrcat("$I$I$I", @"testZipSync => Received: ", [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size], @"  Emitted: ", [c1 get], @" / ", [c2 get])];
  OrgJunitAssert_assertEqualsWithLong_withLong_(NUM, [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size]);
  OrgJunitAssert_assertTrueWithBoolean_([c1 get] < JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 5);
  OrgJunitAssert_assertTrueWithBoolean_([c2 get] < JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 5);
}

- (void)testZipAsync {
  jint NUM = JreFpToInt((JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 2.1));
  JavaUtilConcurrentAtomicAtomicInteger *c1 = create_JavaUtilConcurrentAtomicAtomicInteger_init();
  JavaUtilConcurrentAtomicAtomicInteger *c2 = create_JavaUtilConcurrentAtomicAtomicInteger_init();
  RxObserversTestSubscriber *ts = create_RxObserversTestSubscriber_init();
  RxObservable *zipped = RxObservable_zipWithRxObservable_withRxObservable_withRxFunctionsFunc2_([((RxObservable *) nil_chk(RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(c1))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_computation()], [((RxObservable *) nil_chk(RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(c2))) subscribeOnWithRxScheduler:RxSchedulersSchedulers_computation()], create_RxBackpressureTests_$3_init());
  [((RxObservable *) nil_chk([((RxObservable *) nil_chk(zipped)) takeWithInt:NUM])) subscribeWithRxSubscriber:ts];
  [ts awaitTerminalEvent];
  [ts assertNoErrors];
  [((JavaIoPrintStream *) nil_chk(JreLoadStatic(JavaLangSystem, out))) printlnWithNSString:JreStrcat("$I$I$I", @"testZipAsync => Received: ", [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size], @"  Emitted: ", [c1 get], @" / ", [c2 get])];
  OrgJunitAssert_assertEqualsWithLong_withLong_(NUM, [((id<JavaUtilList>) nil_chk([ts getOnNextEvents])) size]);
  OrgJunitAssert_assertTrueWithBoolean_([c1 get] < JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 3);
  OrgJunitAssert_assertTrueWithBoolean_([c2 get] < JreLoadStatic(RxInternalUtilRxRingBuffer, SIZE) * 3);
}

+ (RxObservable *)incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter {
  return RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(counter);
}

+ (RxObservable *)incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter
                                    withJavaUtilConcurrentConcurrentLinkedQueue:(JavaUtilConcurrentConcurrentLinkedQueue *)threadsSeen {
  return RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(counter, threadsSeen);
}

+ (RxObservable *)firehoseWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter {
  return RxBackpressureTests_firehoseWithJavaUtilConcurrentAtomicAtomicInteger_(counter);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  RxBackpressureTests_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

- (void)dealloc {
  RELEASE_(testName_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, -1, -1, -1, -1, 0, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 1, -1 },
    { NULL, "V", 0x1, -1, -1, -1, -1, 2, -1 },
    { NULL, "LRxObservable;", 0xa, 3, 4, -1, 5, -1, -1 },
    { NULL, "LRxObservable;", 0xa, 3, 6, -1, 7, -1, -1 },
    { NULL, "LRxObservable;", 0xa, 8, 4, -1, 5, -1, -1 },
    { NULL, NULL, 0x1, -1, -1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(doAfterTest);
  methods[1].selector = @selector(testZipSync);
  methods[2].selector = @selector(testZipAsync);
  methods[3].selector = @selector(incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:);
  methods[4].selector = @selector(incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:withJavaUtilConcurrentConcurrentLinkedQueue:);
  methods[5].selector = @selector(firehoseWithJavaUtilConcurrentAtomicAtomicInteger:);
  methods[6].selector = @selector(init);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "testName_", "LOrgJunitRulesTestName;", .constantValue.asLong = 0, 0x1, -1, -1, -1, 9 },
    { "SLOW_PASS_THRU", "LRxFunctionsFunc1;", .constantValue.asLong = 0, 0x18, -1, 10, 11, -1 },
  };
  static const void *ptrTable[] = { (void *)&RxBackpressureTests__Annotations$0, (void *)&RxBackpressureTests__Annotations$1, (void *)&RxBackpressureTests__Annotations$2, "incrementingIntegers", "LJavaUtilConcurrentAtomicAtomicInteger;", "(Ljava/util/concurrent/atomic/AtomicInteger;)Lrx/Observable<Ljava/lang/Integer;>;", "LJavaUtilConcurrentAtomicAtomicInteger;LJavaUtilConcurrentConcurrentLinkedQueue;", "(Ljava/util/concurrent/atomic/AtomicInteger;Ljava/util/concurrent/ConcurrentLinkedQueue<Ljava/lang/Thread;>;)Lrx/Observable<Ljava/lang/Integer;>;", "firehose", (void *)&RxBackpressureTests__Annotations$3, &RxBackpressureTests_SLOW_PASS_THRU, "Lrx/functions/Func1<Ljava/lang/Integer;Ljava/lang/Integer;>;", "LRxBackpressureTests_BPTPRoducer;" };
  static const J2ObjcClassInfo _RxBackpressureTests = { "BackpressureTests", "rx", ptrTable, methods, fields, 7, 0x1, 7, 2, -1, 12, -1, -1, -1 };
  return &_RxBackpressureTests;
}

+ (void)initialize {
  if (self == [RxBackpressureTests class]) {
    JreStrongAssignAndConsume(&RxBackpressureTests_SLOW_PASS_THRU, new_RxBackpressureTests_$1_init());
    J2OBJC_SET_INITIALIZED(RxBackpressureTests)
  }
}

@end

RxObservable *RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *counter) {
  RxBackpressureTests_initialize();
  return RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(counter, nil);
}

RxObservable *RxBackpressureTests_incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *counter, JavaUtilConcurrentConcurrentLinkedQueue *threadsSeen) {
  RxBackpressureTests_initialize();
  return RxObservable_createWithRxObservable_OnSubscribe_(create_RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(counter, threadsSeen));
}

RxObservable *RxBackpressureTests_firehoseWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *counter) {
  RxBackpressureTests_initialize();
  return RxObservable_createWithRxObservable_OnSubscribe_(create_RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(counter));
}

void RxBackpressureTests_init(RxBackpressureTests *self) {
  NSObject_init(self);
  JreStrongAssignAndConsume(&self->testName_, new_OrgJunitRulesTestName_init());
}

RxBackpressureTests *new_RxBackpressureTests_init() {
  J2OBJC_NEW_IMPL(RxBackpressureTests, init)
}

RxBackpressureTests *create_RxBackpressureTests_init() {
  J2OBJC_CREATE_IMPL(RxBackpressureTests, init)
}

IOSObjectArray *RxBackpressureTests__Annotations$0() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitAfter() } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxBackpressureTests__Annotations$1() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxBackpressureTests__Annotations$2() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitTest(OrgJunitTest_None_class_(), 0) } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

IOSObjectArray *RxBackpressureTests__Annotations$3() {
  return [IOSObjectArray arrayWithObjects:(id[]){ create_OrgJunitRule() } count:1 type:JavaLangAnnotationAnnotation_class_()];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(RxBackpressureTests)

@implementation RxBackpressureTests_BPTPRoducer

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)counter
                  withJavaUtilConcurrentConcurrentLinkedQueue:(JavaUtilConcurrentConcurrentLinkedQueue *)threadsSeen
                       withJavaUtilConcurrentAtomicAtomicLong:(JavaUtilConcurrentAtomicAtomicLong *)requested
                                             withRxSubscriber:(RxSubscriber *)s {
  RxBackpressureTests_BPTPRoducer_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_(self, counter, threadsSeen, requested, s);
  return self;
}

- (void)requestWithLong:(jlong)n {
  if (n == 0) {
    return;
  }
  if (threadsSeen_ != nil) {
    [threadsSeen_ offerWithId:JavaLangThread_currentThread()];
  }
  jlong _c = [((JavaUtilConcurrentAtomicAtomicLong *) nil_chk(requested_)) getAndAddWithLong:n];
  if (_c == 0) {
    while (![((RxSubscriber *) nil_chk(s_)) isUnsubscribed]) {
      [((JavaUtilConcurrentAtomicAtomicInteger *) nil_chk(counter_)) incrementAndGet];
      [s_ onNextWithId:JavaLangInteger_valueOfWithInt_(i_++)];
      if ([requested_ decrementAndGet] == 0) {
        return;
      }
    }
  }
}

- (void)dealloc {
  RELEASE_(counter_);
  RELEASE_(threadsSeen_);
  RELEASE_(requested_);
  RELEASE_(s_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, NULL, 0x0, -1, 0, -1, 1, -1, -1 },
    { NULL, "V", 0x1, 2, 3, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(initWithJavaUtilConcurrentAtomicAtomicInteger:withJavaUtilConcurrentConcurrentLinkedQueue:withJavaUtilConcurrentAtomicAtomicLong:withRxSubscriber:);
  methods[1].selector = @selector(requestWithLong:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "counter_", "LJavaUtilConcurrentAtomicAtomicInteger;", .constantValue.asLong = 0, 0x10, -1, -1, -1, -1 },
    { "threadsSeen_", "LJavaUtilConcurrentConcurrentLinkedQueue;", .constantValue.asLong = 0, 0x10, -1, -1, 4, -1 },
    { "requested_", "LJavaUtilConcurrentAtomicAtomicLong;", .constantValue.asLong = 0, 0x10, -1, -1, -1, -1 },
    { "s_", "LRxSubscriber;", .constantValue.asLong = 0, 0x10, -1, -1, 5, -1 },
    { "i_", "I", .constantValue.asLong = 0, 0x0, -1, -1, -1, -1 },
  };
  static const void *ptrTable[] = { "LJavaUtilConcurrentAtomicAtomicInteger;LJavaUtilConcurrentConcurrentLinkedQueue;LJavaUtilConcurrentAtomicAtomicLong;LRxSubscriber;", "(Ljava/util/concurrent/atomic/AtomicInteger;Ljava/util/concurrent/ConcurrentLinkedQueue<Ljava/lang/Thread;>;Ljava/util/concurrent/atomic/AtomicLong;Lrx/Subscriber<-Ljava/lang/Integer;>;)V", "request", "J", "Ljava/util/concurrent/ConcurrentLinkedQueue<Ljava/lang/Thread;>;", "Lrx/Subscriber<-Ljava/lang/Integer;>;", "LRxBackpressureTests;" };
  static const J2ObjcClassInfo _RxBackpressureTests_BPTPRoducer = { "BPTPRoducer", "rx", ptrTable, methods, fields, 7, 0x8, 2, 5, 6, -1, -1, -1, -1 };
  return &_RxBackpressureTests_BPTPRoducer;
}

@end

void RxBackpressureTests_BPTPRoducer_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_(RxBackpressureTests_BPTPRoducer *self, JavaUtilConcurrentAtomicAtomicInteger *counter, JavaUtilConcurrentConcurrentLinkedQueue *threadsSeen, JavaUtilConcurrentAtomicAtomicLong *requested, RxSubscriber *s) {
  NSObject_init(self);
  self->i_ = 0;
  JreStrongAssign(&self->counter_, counter);
  JreStrongAssign(&self->threadsSeen_, threadsSeen);
  JreStrongAssign(&self->requested_, requested);
  JreStrongAssign(&self->s_, ComGoogleJ2objcWeakProxy_forObjectWithId_(s));
}

RxBackpressureTests_BPTPRoducer *new_RxBackpressureTests_BPTPRoducer_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_(JavaUtilConcurrentAtomicAtomicInteger *counter, JavaUtilConcurrentConcurrentLinkedQueue *threadsSeen, JavaUtilConcurrentAtomicAtomicLong *requested, RxSubscriber *s) {
  J2OBJC_NEW_IMPL(RxBackpressureTests_BPTPRoducer, initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_, counter, threadsSeen, requested, s)
}

RxBackpressureTests_BPTPRoducer *create_RxBackpressureTests_BPTPRoducer_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_(JavaUtilConcurrentAtomicAtomicInteger *counter, JavaUtilConcurrentConcurrentLinkedQueue *threadsSeen, JavaUtilConcurrentAtomicAtomicLong *requested, RxSubscriber *s) {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_BPTPRoducer, initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_, counter, threadsSeen, requested, s)
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(RxBackpressureTests_BPTPRoducer)

@implementation RxBackpressureTests_$2

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1
                         withId:(JavaLangInteger *)t2 {
  return JavaLangInteger_valueOfWithInt_([((JavaLangInteger *) nil_chk(t1)) intValue] + [((JavaLangInteger *) nil_chk(t2)) intValue]);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  RxBackpressureTests_$2_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "LJavaLangInteger;", 0x1, 0, 1, -1, -1, -1, -1 },
    { NULL, NULL, 0x0, -1, -1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(callWithId:withId:);
  methods[1].selector = @selector(init);
  #pragma clang diagnostic pop
  static const void *ptrTable[] = { "call", "LJavaLangInteger;LJavaLangInteger;", "LRxBackpressureTests;", "testZipSync", "Ljava/lang/Object;Lrx/functions/Func2<Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxBackpressureTests_$2 = { "", "rx", ptrTable, methods, NULL, 7, 0x8008, 2, 0, 2, -1, 3, 4, -1 };
  return &_RxBackpressureTests_$2;
}

@end

void RxBackpressureTests_$2_init(RxBackpressureTests_$2 *self) {
  NSObject_init(self);
}

RxBackpressureTests_$2 *new_RxBackpressureTests_$2_init() {
  J2OBJC_NEW_IMPL(RxBackpressureTests_$2, init)
}

RxBackpressureTests_$2 *create_RxBackpressureTests_$2_init() {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_$2, init)
}

@implementation RxBackpressureTests_$3

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1
                         withId:(JavaLangInteger *)t2 {
  return JavaLangInteger_valueOfWithInt_([((JavaLangInteger *) nil_chk(t1)) intValue] + [((JavaLangInteger *) nil_chk(t2)) intValue]);
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  RxBackpressureTests_$3_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "LJavaLangInteger;", 0x1, 0, 1, -1, -1, -1, -1 },
    { NULL, NULL, 0x0, -1, -1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(callWithId:withId:);
  methods[1].selector = @selector(init);
  #pragma clang diagnostic pop
  static const void *ptrTable[] = { "call", "LJavaLangInteger;LJavaLangInteger;", "LRxBackpressureTests;", "testZipAsync", "Ljava/lang/Object;Lrx/functions/Func2<Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxBackpressureTests_$3 = { "", "rx", ptrTable, methods, NULL, 7, 0x8008, 2, 0, 2, -1, 3, 4, -1 };
  return &_RxBackpressureTests_$3;
}

@end

void RxBackpressureTests_$3_init(RxBackpressureTests_$3 *self) {
  NSObject_init(self);
}

RxBackpressureTests_$3 *new_RxBackpressureTests_$3_init() {
  J2OBJC_NEW_IMPL(RxBackpressureTests_$3, init)
}

RxBackpressureTests_$3 *create_RxBackpressureTests_$3_init() {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_$3, init)
}

@implementation RxBackpressureTests_$4

- (void)callWithId:(RxSubscriber *)s {
  [((RxSubscriber *) nil_chk(s)) setProducerWithRxProducer:create_RxBackpressureTests_BPTPRoducer_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_withJavaUtilConcurrentAtomicAtomicLong_withRxSubscriber_(val$counter_, val$threadsSeen_, requested_, s)];
}

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)capture$0
                  withJavaUtilConcurrentConcurrentLinkedQueue:(JavaUtilConcurrentConcurrentLinkedQueue *)capture$1 {
  RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(self, capture$0, capture$1);
  return self;
}

- (void)dealloc {
  RELEASE_(requested_);
  RELEASE_(val$counter_);
  RELEASE_(val$threadsSeen_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, 0, 1, -1, 2, -1, -1 },
    { NULL, NULL, 0x0, -1, 3, -1, 4, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(callWithId:);
  methods[1].selector = @selector(initWithJavaUtilConcurrentAtomicAtomicInteger:withJavaUtilConcurrentConcurrentLinkedQueue:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "requested_", "LJavaUtilConcurrentAtomicAtomicLong;", .constantValue.asLong = 0, 0x10, -1, -1, -1, -1 },
    { "val$counter_", "LJavaUtilConcurrentAtomicAtomicInteger;", .constantValue.asLong = 0, 0x1012, -1, -1, -1, -1 },
    { "val$threadsSeen_", "LJavaUtilConcurrentConcurrentLinkedQueue;", .constantValue.asLong = 0, 0x1012, -1, -1, 5, -1 },
  };
  static const void *ptrTable[] = { "call", "LRxSubscriber;", "(Lrx/Subscriber<-Ljava/lang/Integer;>;)V", "LJavaUtilConcurrentAtomicAtomicInteger;LJavaUtilConcurrentConcurrentLinkedQueue;", "(Ljava/util/concurrent/atomic/AtomicInteger;Ljava/util/concurrent/ConcurrentLinkedQueue<Ljava/lang/Thread;>;)V", "Ljava/util/concurrent/ConcurrentLinkedQueue<Ljava/lang/Thread;>;", "LRxBackpressureTests;", "incrementingIntegersWithJavaUtilConcurrentAtomicAtomicInteger:withJavaUtilConcurrentConcurrentLinkedQueue:", "Ljava/lang/Object;Lrx/Observable$OnSubscribe<Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxBackpressureTests_$4 = { "", "rx", ptrTable, methods, fields, 7, 0x8008, 2, 3, 6, -1, 7, 8, -1 };
  return &_RxBackpressureTests_$4;
}

@end

void RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(RxBackpressureTests_$4 *self, JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1) {
  JreStrongAssign(&self->val$counter_, capture$0);
  JreStrongAssign(&self->val$threadsSeen_, capture$1);
  NSObject_init(self);
  JreStrongAssignAndConsume(&self->requested_, new_JavaUtilConcurrentAtomicAtomicLong_init());
}

RxBackpressureTests_$4 *new_RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1) {
  J2OBJC_NEW_IMPL(RxBackpressureTests_$4, initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_, capture$0, capture$1)
}

RxBackpressureTests_$4 *create_RxBackpressureTests_$4_initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_(JavaUtilConcurrentAtomicAtomicInteger *capture$0, JavaUtilConcurrentConcurrentLinkedQueue *capture$1) {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_$4, initWithJavaUtilConcurrentAtomicAtomicInteger_withJavaUtilConcurrentConcurrentLinkedQueue_, capture$0, capture$1)
}

@implementation RxBackpressureTests_$5

- (void)callWithId:(RxSubscriber *)s {
  while (![((RxSubscriber *) nil_chk(s)) isUnsubscribed]) {
    [s onNextWithId:JavaLangInteger_valueOfWithInt_(i_++)];
    [((JavaUtilConcurrentAtomicAtomicInteger *) nil_chk(val$counter_)) incrementAndGet];
  }
  [((JavaIoPrintStream *) nil_chk(JreLoadStatic(JavaLangSystem, out))) printlnWithNSString:JreStrcat("$I", @"unsubscribed after: ", i_)];
}

- (instancetype)initWithJavaUtilConcurrentAtomicAtomicInteger:(JavaUtilConcurrentAtomicAtomicInteger *)capture$0 {
  RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(self, capture$0);
  return self;
}

- (void)dealloc {
  RELEASE_(val$counter_);
  [super dealloc];
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "V", 0x1, 0, 1, -1, 2, -1, -1 },
    { NULL, NULL, 0x0, -1, 3, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(callWithId:);
  methods[1].selector = @selector(initWithJavaUtilConcurrentAtomicAtomicInteger:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "i_", "I", .constantValue.asLong = 0, 0x0, -1, -1, -1, -1 },
    { "val$counter_", "LJavaUtilConcurrentAtomicAtomicInteger;", .constantValue.asLong = 0, 0x1012, -1, -1, -1, -1 },
  };
  static const void *ptrTable[] = { "call", "LRxSubscriber;", "(Lrx/Subscriber<-Ljava/lang/Integer;>;)V", "LJavaUtilConcurrentAtomicAtomicInteger;", "LRxBackpressureTests;", "firehoseWithJavaUtilConcurrentAtomicAtomicInteger:", "Ljava/lang/Object;Lrx/Observable$OnSubscribe<Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxBackpressureTests_$5 = { "", "rx", ptrTable, methods, fields, 7, 0x8008, 2, 2, 4, -1, 5, 6, -1 };
  return &_RxBackpressureTests_$5;
}

@end

void RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(RxBackpressureTests_$5 *self, JavaUtilConcurrentAtomicAtomicInteger *capture$0) {
  JreStrongAssign(&self->val$counter_, capture$0);
  NSObject_init(self);
  self->i_ = 0;
}

RxBackpressureTests_$5 *new_RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *capture$0) {
  J2OBJC_NEW_IMPL(RxBackpressureTests_$5, initWithJavaUtilConcurrentAtomicAtomicInteger_, capture$0)
}

RxBackpressureTests_$5 *create_RxBackpressureTests_$5_initWithJavaUtilConcurrentAtomicAtomicInteger_(JavaUtilConcurrentAtomicAtomicInteger *capture$0) {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_$5, initWithJavaUtilConcurrentAtomicAtomicInteger_, capture$0)
}

@implementation RxBackpressureTests_$1

- (JavaLangInteger *)callWithId:(JavaLangInteger *)t1 {
  NSString *t = @"";
  jint s = JreLoadVolatileInt(&sink_);
  for (jint i = 1000; i >= 0; i--) {
    t = NSString_valueOfInt_(i + ((jint) [((NSString *) nil_chk(t)) hash]) + s);
  }
  JreAssignVolatileInt(&sink_, ((jint) [((NSString *) nil_chk(t)) hash]));
  return t1;
}

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  RxBackpressureTests_$1_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, "LJavaLangInteger;", 0x1, 0, 1, -1, -1, -1, -1 },
    { NULL, NULL, 0x0, -1, -1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(callWithId:);
  methods[1].selector = @selector(init);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "sink_", "I", .constantValue.asLong = 0, 0x40, -1, -1, -1, -1 },
  };
  static const void *ptrTable[] = { "call", "LJavaLangInteger;", "LRxBackpressureTests;", "Ljava/lang/Object;Lrx/functions/Func1<Ljava/lang/Integer;Ljava/lang/Integer;>;" };
  static const J2ObjcClassInfo _RxBackpressureTests_$1 = { "", "rx", ptrTable, methods, fields, 7, 0x8008, 2, 1, 2, -1, -1, 3, -1 };
  return &_RxBackpressureTests_$1;
}

@end

void RxBackpressureTests_$1_init(RxBackpressureTests_$1 *self) {
  NSObject_init(self);
}

RxBackpressureTests_$1 *new_RxBackpressureTests_$1_init() {
  J2OBJC_NEW_IMPL(RxBackpressureTests_$1, init)
}

RxBackpressureTests_$1 *create_RxBackpressureTests_$1_init() {
  J2OBJC_CREATE_IMPL(RxBackpressureTests_$1, init)
}
