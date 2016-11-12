//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kgalligan/devel-doppl/RxJava/src/main/java/rx/internal/operators/OperatorMerge.java
//

#include "J2ObjC_header.h"

#pragma push_macro("INCLUDE_ALL_RxInternalOperatorsOperatorMerge")
#ifdef RESTRICT_RxInternalOperatorsOperatorMerge
#define INCLUDE_ALL_RxInternalOperatorsOperatorMerge 0
#else
#define INCLUDE_ALL_RxInternalOperatorsOperatorMerge 1
#endif
#undef RESTRICT_RxInternalOperatorsOperatorMerge

#if !defined (RxInternalOperatorsOperatorMerge_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge))
#define RxInternalOperatorsOperatorMerge_

#define RESTRICT_RxObservable 1
#define INCLUDE_RxObservable_Operator 1
#include "RxObservable.h"

@class RxSubscriber;

@interface RxInternalOperatorsOperatorMerge : NSObject < RxObservable_Operator > {
 @public
  jboolean delayErrors_;
  jint maxConcurrent_;
}

#pragma mark Public

- (RxSubscriber *)callWithId:(RxSubscriber *)child;

+ (RxInternalOperatorsOperatorMerge *)instanceWithBoolean:(jboolean)delayErrors;

+ (RxInternalOperatorsOperatorMerge *)instanceWithBoolean:(jboolean)delayErrors
                                                  withInt:(jint)maxConcurrent;

#pragma mark Package-Private

- (instancetype)initWithBoolean:(jboolean)delayErrors
                        withInt:(jint)maxConcurrent;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOperatorMerge)

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_instanceWithBoolean_(jboolean delayErrors);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_instanceWithBoolean_withInt_(jboolean delayErrors, jint maxConcurrent);

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_initWithBoolean_withInt_(RxInternalOperatorsOperatorMerge *self, jboolean delayErrors, jint maxConcurrent);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *new_RxInternalOperatorsOperatorMerge_initWithBoolean_withInt_(jboolean delayErrors, jint maxConcurrent) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *create_RxInternalOperatorsOperatorMerge_initWithBoolean_withInt_(jboolean delayErrors, jint maxConcurrent);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge)

#endif

#if !defined (RxInternalOperatorsOperatorMerge_HolderNoDelay_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge_HolderNoDelay))
#define RxInternalOperatorsOperatorMerge_HolderNoDelay_

@class RxInternalOperatorsOperatorMerge;

@interface RxInternalOperatorsOperatorMerge_HolderNoDelay : NSObject

#pragma mark Package-Private

- (instancetype)init;

@end

J2OBJC_STATIC_INIT(RxInternalOperatorsOperatorMerge_HolderNoDelay)

inline RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_HolderNoDelay_get_INSTANCE();
/*! INTERNAL ONLY - Use accessor function from above. */
FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_HolderNoDelay_INSTANCE;
J2OBJC_STATIC_FIELD_OBJ_FINAL(RxInternalOperatorsOperatorMerge_HolderNoDelay, INSTANCE, RxInternalOperatorsOperatorMerge *)

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_HolderNoDelay_init(RxInternalOperatorsOperatorMerge_HolderNoDelay *self);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_HolderNoDelay *new_RxInternalOperatorsOperatorMerge_HolderNoDelay_init() NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_HolderNoDelay *create_RxInternalOperatorsOperatorMerge_HolderNoDelay_init();

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge_HolderNoDelay)

#endif

#if !defined (RxInternalOperatorsOperatorMerge_HolderDelayErrors_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge_HolderDelayErrors))
#define RxInternalOperatorsOperatorMerge_HolderDelayErrors_

@class RxInternalOperatorsOperatorMerge;

@interface RxInternalOperatorsOperatorMerge_HolderDelayErrors : NSObject

#pragma mark Package-Private

- (instancetype)init;

@end

J2OBJC_STATIC_INIT(RxInternalOperatorsOperatorMerge_HolderDelayErrors)

inline RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_HolderDelayErrors_get_INSTANCE();
/*! INTERNAL ONLY - Use accessor function from above. */
FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge *RxInternalOperatorsOperatorMerge_HolderDelayErrors_INSTANCE;
J2OBJC_STATIC_FIELD_OBJ_FINAL(RxInternalOperatorsOperatorMerge_HolderDelayErrors, INSTANCE, RxInternalOperatorsOperatorMerge *)

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_HolderDelayErrors_init(RxInternalOperatorsOperatorMerge_HolderDelayErrors *self);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_HolderDelayErrors *new_RxInternalOperatorsOperatorMerge_HolderDelayErrors_init() NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_HolderDelayErrors *create_RxInternalOperatorsOperatorMerge_HolderDelayErrors_init();

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge_HolderDelayErrors)

#endif

#if !defined (RxInternalOperatorsOperatorMerge_MergeProducer_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge_MergeProducer))
#define RxInternalOperatorsOperatorMerge_MergeProducer_

#define RESTRICT_JavaUtilConcurrentAtomicAtomicLong 1
#define INCLUDE_JavaUtilConcurrentAtomicAtomicLong 1
#include "java/util/concurrent/atomic/AtomicLong.h"

#define RESTRICT_RxProducer 1
#define INCLUDE_RxProducer 1
#include "RxProducer.h"

@class RxInternalOperatorsOperatorMerge_MergeSubscriber;

@interface RxInternalOperatorsOperatorMerge_MergeProducer : JavaUtilConcurrentAtomicAtomicLong < RxProducer > {
 @public
  __unsafe_unretained RxInternalOperatorsOperatorMerge_MergeSubscriber *subscriber_;
}

#pragma mark Public

- (instancetype)initWithRxInternalOperatorsOperatorMerge_MergeSubscriber:(RxInternalOperatorsOperatorMerge_MergeSubscriber *)subscriber;

- (jlong)producedWithInt:(jint)n;

- (void)requestWithLong:(jlong)n;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOperatorMerge_MergeProducer)

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_MergeProducer_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_(RxInternalOperatorsOperatorMerge_MergeProducer *self, RxInternalOperatorsOperatorMerge_MergeSubscriber *subscriber);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_MergeProducer *new_RxInternalOperatorsOperatorMerge_MergeProducer_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_(RxInternalOperatorsOperatorMerge_MergeSubscriber *subscriber) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_MergeProducer *create_RxInternalOperatorsOperatorMerge_MergeProducer_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_(RxInternalOperatorsOperatorMerge_MergeSubscriber *subscriber);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge_MergeProducer)

#endif

#if !defined (RxInternalOperatorsOperatorMerge_MergeSubscriber_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge_MergeSubscriber))
#define RxInternalOperatorsOperatorMerge_MergeSubscriber_

#define RESTRICT_RxSubscriber 1
#define INCLUDE_RxSubscriber 1
#include "RxSubscriber.h"

@class IOSObjectArray;
@class JavaUtilConcurrentConcurrentLinkedQueue;
@class RxInternalOperatorsOperatorMerge_InnerSubscriber;
@class RxInternalOperatorsOperatorMerge_MergeProducer;
@class RxObservable;
@class RxSubscriptionsCompositeSubscription;
@protocol JavaUtilQueue;

@interface RxInternalOperatorsOperatorMerge_MergeSubscriber : RxSubscriber {
 @public
  __unsafe_unretained RxSubscriber *child_;
  jboolean delayErrors_;
  jint maxConcurrent_;
  RxInternalOperatorsOperatorMerge_MergeProducer *producer_;
  volatile_id queue_;
  volatile_id subscriptions_MergeSubscriber_;
  volatile_id errors_;
  volatile_jboolean done_;
  jboolean emitting_;
  jboolean missed_;
  id innerGuard_;
  volatile_id innerSubscribers_;
  jlong uniqueId_;
  jlong lastId_;
  jint lastIndex_;
  jint scalarEmissionLimit_;
  jint scalarEmissionCount_;
}

#pragma mark Public

- (instancetype)initWithRxSubscriber:(RxSubscriber *)child
                         withBoolean:(jboolean)delayErrors
                             withInt:(jint)maxConcurrent;

- (void)onCompleted;

- (void)onErrorWithNSException:(NSException *)e;

- (void)onNextWithId:(RxObservable *)t;

- (void)requestMoreWithLong:(jlong)n;

#pragma mark Protected

- (void)emitScalarWithRxInternalOperatorsOperatorMerge_InnerSubscriber:(RxInternalOperatorsOperatorMerge_InnerSubscriber *)subscriber
                                                                withId:(id)value
                                                              withLong:(jlong)r;

- (void)emitScalarWithId:(id)value
                withLong:(jlong)r;

- (void)queueScalarWithRxInternalOperatorsOperatorMerge_InnerSubscriber:(RxInternalOperatorsOperatorMerge_InnerSubscriber *)subscriber
                                                                 withId:(id)value;

- (void)queueScalarWithId:(id)value;

#pragma mark Package-Private

- (void)addInnerWithRxInternalOperatorsOperatorMerge_InnerSubscriber:(RxInternalOperatorsOperatorMerge_InnerSubscriber *)inner;

- (jboolean)checkTerminate;

- (void)emit;

- (void)emitEmpty;

- (void)emitLoop;

- (RxSubscriptionsCompositeSubscription *)getOrCreateComposite;

- (id<JavaUtilQueue>)getOrCreateErrorQueue;

- (void)removeInnerWithRxInternalOperatorsOperatorMerge_InnerSubscriber:(RxInternalOperatorsOperatorMerge_InnerSubscriber *)inner;

- (void)tryEmitWithRxInternalOperatorsOperatorMerge_InnerSubscriber:(RxInternalOperatorsOperatorMerge_InnerSubscriber *)subscriber
                                                             withId:(id)value;

- (void)tryEmitWithId:(id)value;

@end

J2OBJC_STATIC_INIT(RxInternalOperatorsOperatorMerge_MergeSubscriber)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, producer_, RxInternalOperatorsOperatorMerge_MergeProducer *)
J2OBJC_VOLATILE_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, queue_, id<JavaUtilQueue>)
J2OBJC_VOLATILE_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, subscriptions_MergeSubscriber_, RxSubscriptionsCompositeSubscription *)
J2OBJC_VOLATILE_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, errors_, JavaUtilConcurrentConcurrentLinkedQueue *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, innerGuard_, id)
J2OBJC_VOLATILE_FIELD_SETTER(RxInternalOperatorsOperatorMerge_MergeSubscriber, innerSubscribers_, IOSObjectArray *)

inline IOSObjectArray *RxInternalOperatorsOperatorMerge_MergeSubscriber_get_EMPTY();
/*! INTERNAL ONLY - Use accessor function from above. */
FOUNDATION_EXPORT IOSObjectArray *RxInternalOperatorsOperatorMerge_MergeSubscriber_EMPTY;
J2OBJC_STATIC_FIELD_OBJ_FINAL(RxInternalOperatorsOperatorMerge_MergeSubscriber, EMPTY, IOSObjectArray *)

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_MergeSubscriber_initWithRxSubscriber_withBoolean_withInt_(RxInternalOperatorsOperatorMerge_MergeSubscriber *self, RxSubscriber *child, jboolean delayErrors, jint maxConcurrent);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_MergeSubscriber *new_RxInternalOperatorsOperatorMerge_MergeSubscriber_initWithRxSubscriber_withBoolean_withInt_(RxSubscriber *child, jboolean delayErrors, jint maxConcurrent) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_MergeSubscriber *create_RxInternalOperatorsOperatorMerge_MergeSubscriber_initWithRxSubscriber_withBoolean_withInt_(RxSubscriber *child, jboolean delayErrors, jint maxConcurrent);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge_MergeSubscriber)

#endif

#if !defined (RxInternalOperatorsOperatorMerge_InnerSubscriber_) && (INCLUDE_ALL_RxInternalOperatorsOperatorMerge || defined(INCLUDE_RxInternalOperatorsOperatorMerge_InnerSubscriber))
#define RxInternalOperatorsOperatorMerge_InnerSubscriber_

#define RESTRICT_RxSubscriber 1
#define INCLUDE_RxSubscriber 1
#include "RxSubscriber.h"

@class RxInternalOperatorsOperatorMerge_MergeSubscriber;
@class RxInternalUtilRxRingBuffer;

@interface RxInternalOperatorsOperatorMerge_InnerSubscriber : RxSubscriber {
 @public
  __unsafe_unretained RxInternalOperatorsOperatorMerge_MergeSubscriber *parent_;
  jlong id__;
  volatile_jboolean done_;
  volatile_id queue_;
  jint outstanding_;
}

#pragma mark Public

- (instancetype)initWithRxInternalOperatorsOperatorMerge_MergeSubscriber:(RxInternalOperatorsOperatorMerge_MergeSubscriber *)parent
                                                                withLong:(jlong)id_;

- (void)onCompleted;

- (void)onErrorWithNSException:(NSException *)e;

- (void)onNextWithId:(id)t;

- (void)onStart;

- (void)requestMoreWithLong:(jlong)n;

@end

J2OBJC_STATIC_INIT(RxInternalOperatorsOperatorMerge_InnerSubscriber)

J2OBJC_VOLATILE_FIELD_SETTER(RxInternalOperatorsOperatorMerge_InnerSubscriber, queue_, RxInternalUtilRxRingBuffer *)

inline jint RxInternalOperatorsOperatorMerge_InnerSubscriber_get_LIMIT();
/*! INTERNAL ONLY - Use accessor function from above. */
FOUNDATION_EXPORT jint RxInternalOperatorsOperatorMerge_InnerSubscriber_LIMIT;
J2OBJC_STATIC_FIELD_PRIMITIVE_FINAL(RxInternalOperatorsOperatorMerge_InnerSubscriber, LIMIT, jint)

FOUNDATION_EXPORT void RxInternalOperatorsOperatorMerge_InnerSubscriber_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_withLong_(RxInternalOperatorsOperatorMerge_InnerSubscriber *self, RxInternalOperatorsOperatorMerge_MergeSubscriber *parent, jlong id_);

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_InnerSubscriber *new_RxInternalOperatorsOperatorMerge_InnerSubscriber_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_withLong_(RxInternalOperatorsOperatorMerge_MergeSubscriber *parent, jlong id_) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOperatorMerge_InnerSubscriber *create_RxInternalOperatorsOperatorMerge_InnerSubscriber_initWithRxInternalOperatorsOperatorMerge_MergeSubscriber_withLong_(RxInternalOperatorsOperatorMerge_MergeSubscriber *parent, jlong id_);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOperatorMerge_InnerSubscriber)

#endif

#pragma pop_macro("INCLUDE_ALL_RxInternalOperatorsOperatorMerge")
