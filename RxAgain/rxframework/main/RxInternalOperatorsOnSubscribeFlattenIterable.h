//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kgalligan/devel-doppl/RxJava/src/main/java/rx/internal/operators/OnSubscribeFlattenIterable.java
//

#include "J2ObjC_header.h"

#pragma push_macro("INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable")
#ifdef RESTRICT_RxInternalOperatorsOnSubscribeFlattenIterable
#define INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable 0
#else
#define INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable 1
#endif
#undef RESTRICT_RxInternalOperatorsOnSubscribeFlattenIterable

#if !defined (RxInternalOperatorsOnSubscribeFlattenIterable_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable || defined(INCLUDE_RxInternalOperatorsOnSubscribeFlattenIterable))
#define RxInternalOperatorsOnSubscribeFlattenIterable_

#define RESTRICT_RxObservable 1
#define INCLUDE_RxObservable_OnSubscribe 1
#include "RxObservable.h"

@class RxDopplSafeObservableUnsubscribe;
@class RxObservable;
@class RxSubscriber;
@protocol RxFunctionsFunc1;

@interface RxInternalOperatorsOnSubscribeFlattenIterable : NSObject < RxObservable_OnSubscribe > {
 @public
  RxDopplSafeObservableUnsubscribe *source_;
  id<RxFunctionsFunc1> mapper_;
  jint prefetch_;
}

#pragma mark Public

- (void)callWithId:(RxSubscriber *)t;

+ (RxObservable *)createFromWithRxObservable:(RxObservable *)source
                        withRxFunctionsFunc1:(id<RxFunctionsFunc1>)mapper
                                     withInt:(jint)prefetch;

#pragma mark Protected

- (instancetype)initWithRxObservable:(RxObservable *)source
                withRxFunctionsFunc1:(id<RxFunctionsFunc1>)mapper
                             withInt:(jint)prefetch;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeFlattenIterable)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable, source_, RxDopplSafeObservableUnsubscribe *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable, mapper_, id<RxFunctionsFunc1>)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeFlattenIterable_initWithRxObservable_withRxFunctionsFunc1_withInt_(RxInternalOperatorsOnSubscribeFlattenIterable *self, RxObservable *source, id<RxFunctionsFunc1> mapper, jint prefetch);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable *new_RxInternalOperatorsOnSubscribeFlattenIterable_initWithRxObservable_withRxFunctionsFunc1_withInt_(RxObservable *source, id<RxFunctionsFunc1> mapper, jint prefetch) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable *create_RxInternalOperatorsOnSubscribeFlattenIterable_initWithRxObservable_withRxFunctionsFunc1_withInt_(RxObservable *source, id<RxFunctionsFunc1> mapper, jint prefetch);

FOUNDATION_EXPORT RxObservable *RxInternalOperatorsOnSubscribeFlattenIterable_createFromWithRxObservable_withRxFunctionsFunc1_withInt_(RxObservable *source, id<RxFunctionsFunc1> mapper, jint prefetch);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeFlattenIterable)

#endif

#if !defined (RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable || defined(INCLUDE_RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber))
#define RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber_

#define RESTRICT_RxSubscriber 1
#define INCLUDE_RxSubscriber 1
#include "RxSubscriber.h"

@class JavaUtilConcurrentAtomicAtomicInteger;
@class JavaUtilConcurrentAtomicAtomicLong;
@class JavaUtilConcurrentAtomicAtomicReference;
@protocol JavaUtilIterator;
@protocol JavaUtilQueue;
@protocol RxFunctionsFunc1;

@interface RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber : RxSubscriber {
 @public
  RxSubscriber *actual_;
  id<RxFunctionsFunc1> mapper_;
  jlong limit_;
  id<JavaUtilQueue> queue_;
  JavaUtilConcurrentAtomicAtomicReference *error_;
  JavaUtilConcurrentAtomicAtomicLong *requested_FlattenIterableSubscriber_;
  JavaUtilConcurrentAtomicAtomicInteger *wip_;
  volatile_jboolean done_;
  jlong produced_;
  id<JavaUtilIterator> active_;
}

#pragma mark Public

- (instancetype)initWithRxSubscriber:(RxSubscriber *)actual
                withRxFunctionsFunc1:(id<RxFunctionsFunc1>)mapper
                             withInt:(jint)prefetch;

- (void)j2objcCleanup;

- (void)onCompleted;

- (void)onErrorWithNSException:(NSException *)e;

- (void)onNextWithId:(id)t;

#pragma mark Package-Private

- (jboolean)checkTerminatedWithBoolean:(jboolean)d
                           withBoolean:(jboolean)empty
                      withRxSubscriber:(RxSubscriber *)a
                     withJavaUtilQueue:(id<JavaUtilQueue>)q;

- (void)drain;

- (void)requestMoreWithLong:(jlong)n;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, actual_, RxSubscriber *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, mapper_, id<RxFunctionsFunc1>)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, queue_, id<JavaUtilQueue>)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, error_, JavaUtilConcurrentAtomicAtomicReference *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, requested_FlattenIterableSubscriber_, JavaUtilConcurrentAtomicAtomicLong *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, wip_, JavaUtilConcurrentAtomicAtomicInteger *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber, active_, id<JavaUtilIterator>)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber_initWithRxSubscriber_withRxFunctionsFunc1_withInt_(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber *self, RxSubscriber *actual, id<RxFunctionsFunc1> mapper, jint prefetch);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber *new_RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber_initWithRxSubscriber_withRxFunctionsFunc1_withInt_(RxSubscriber *actual, id<RxFunctionsFunc1> mapper, jint prefetch) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber *create_RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber_initWithRxSubscriber_withRxFunctionsFunc1_withInt_(RxSubscriber *actual, id<RxFunctionsFunc1> mapper, jint prefetch);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeFlattenIterable_FlattenIterableSubscriber)

#endif

#if !defined (RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable || defined(INCLUDE_RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable))
#define RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable_

#define RESTRICT_RxObservable 1
#define INCLUDE_RxObservable_OnSubscribe 1
#include "RxObservable.h"

@class RxSubscriber;
@protocol RxFunctionsFunc1;

@interface RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable : NSObject < RxObservable_OnSubscribe > {
 @public
  id value_;
  id<RxFunctionsFunc1> mapper_;
}

#pragma mark Public

- (instancetype)initWithId:(id)value
      withRxFunctionsFunc1:(id<RxFunctionsFunc1>)mapper;

- (void)callWithId:(RxSubscriber *)t;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable, value_, id)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable, mapper_, id<RxFunctionsFunc1>)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable_initWithId_withRxFunctionsFunc1_(RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable *self, id value, id<RxFunctionsFunc1> mapper);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable *new_RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable_initWithId_withRxFunctionsFunc1_(id value, id<RxFunctionsFunc1> mapper) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable *create_RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable_initWithId_withRxFunctionsFunc1_(id value, id<RxFunctionsFunc1> mapper);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeFlattenIterable_OnSubscribeScalarFlattenIterable)

#endif

#pragma pop_macro("INCLUDE_ALL_RxInternalOperatorsOnSubscribeFlattenIterable")
