//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/kgalligan/devel-doppl/RxJava/src/main/java/rx/internal/operators/OnSubscribeDetach.java
//

#include "J2ObjC_header.h"

#pragma push_macro("INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach")
#ifdef RESTRICT_RxInternalOperatorsOnSubscribeDetach
#define INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach 0
#else
#define INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach 1
#endif
#undef RESTRICT_RxInternalOperatorsOnSubscribeDetach

#if !defined (RxInternalOperatorsOnSubscribeDetach_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach || defined(INCLUDE_RxInternalOperatorsOnSubscribeDetach))
#define RxInternalOperatorsOnSubscribeDetach_

#define RESTRICT_RxObservable 1
#define INCLUDE_RxObservable_OnSubscribe 1
#include "RxObservable.h"

@class RxObservable;
@class RxSubscriber;

@interface RxInternalOperatorsOnSubscribeDetach : NSObject < RxObservable_OnSubscribe > {
 @public
  RxObservable *source_;
}

#pragma mark Public

- (instancetype)initWithRxObservable:(RxObservable *)source;

- (void)callWithId:(RxSubscriber *)t;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeDetach)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeDetach, source_, RxObservable *)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeDetach_initWithRxObservable_(RxInternalOperatorsOnSubscribeDetach *self, RxObservable *source);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach *new_RxInternalOperatorsOnSubscribeDetach_initWithRxObservable_(RxObservable *source) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach *create_RxInternalOperatorsOnSubscribeDetach_initWithRxObservable_(RxObservable *source);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeDetach)

#endif

#if !defined (RxInternalOperatorsOnSubscribeDetach_DetachSubscriber_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach || defined(INCLUDE_RxInternalOperatorsOnSubscribeDetach_DetachSubscriber))
#define RxInternalOperatorsOnSubscribeDetach_DetachSubscriber_

#define RESTRICT_RxSubscriber 1
#define INCLUDE_RxSubscriber 1
#include "RxSubscriber.h"

@class JavaUtilConcurrentAtomicAtomicLong;
@class JavaUtilConcurrentAtomicAtomicReference;
@protocol RxProducer;

@interface RxInternalOperatorsOnSubscribeDetach_DetachSubscriber : RxSubscriber {
 @public
  JavaUtilConcurrentAtomicAtomicReference *actual_;
  JavaUtilConcurrentAtomicAtomicReference *producer_;
  JavaUtilConcurrentAtomicAtomicLong *requested_DetachSubscriber_;
}

#pragma mark Public

- (instancetype)initWithRxSubscriber:(RxSubscriber *)actual;

- (void)onCompleted;

- (void)onErrorWithNSException:(NSException *)e;

- (void)onNextWithId:(id)t;

- (void)setProducerWithRxProducer:(id<RxProducer>)p;

#pragma mark Package-Private

- (void)innerRequestWithLong:(jlong)n;

- (void)innerUnsubscribe;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber)

J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber, actual_, JavaUtilConcurrentAtomicAtomicReference *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber, producer_, JavaUtilConcurrentAtomicAtomicReference *)
J2OBJC_FIELD_SETTER(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber, requested_DetachSubscriber_, JavaUtilConcurrentAtomicAtomicLong *)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeDetach_DetachSubscriber_initWithRxSubscriber_(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *self, RxSubscriber *actual);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *new_RxInternalOperatorsOnSubscribeDetach_DetachSubscriber_initWithRxSubscriber_(RxSubscriber *actual) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *create_RxInternalOperatorsOnSubscribeDetach_DetachSubscriber_initWithRxSubscriber_(RxSubscriber *actual);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber)

#endif

#if !defined (RxInternalOperatorsOnSubscribeDetach_DetachProducer_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach || defined(INCLUDE_RxInternalOperatorsOnSubscribeDetach_DetachProducer))
#define RxInternalOperatorsOnSubscribeDetach_DetachProducer_

#define RESTRICT_RxProducer 1
#define INCLUDE_RxProducer 1
#include "RxProducer.h"

#define RESTRICT_RxSubscription 1
#define INCLUDE_RxSubscription 1
#include "RxSubscription.h"

@class RxInternalOperatorsOnSubscribeDetach_DetachSubscriber;

@interface RxInternalOperatorsOnSubscribeDetach_DetachProducer : NSObject < RxProducer, RxSubscription > {
 @public
  __unsafe_unretained RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *parent_;
}

#pragma mark Public

- (instancetype)initWithRxInternalOperatorsOnSubscribeDetach_DetachSubscriber:(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *)parent;

- (jboolean)isUnsubscribed;

- (void)requestWithLong:(jlong)n;

- (void)unsubscribe;

@end

J2OBJC_EMPTY_STATIC_INIT(RxInternalOperatorsOnSubscribeDetach_DetachProducer)

FOUNDATION_EXPORT void RxInternalOperatorsOnSubscribeDetach_DetachProducer_initWithRxInternalOperatorsOnSubscribeDetach_DetachSubscriber_(RxInternalOperatorsOnSubscribeDetach_DetachProducer *self, RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *parent);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_DetachProducer *new_RxInternalOperatorsOnSubscribeDetach_DetachProducer_initWithRxInternalOperatorsOnSubscribeDetach_DetachSubscriber_(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *parent) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_DetachProducer *create_RxInternalOperatorsOnSubscribeDetach_DetachProducer_initWithRxInternalOperatorsOnSubscribeDetach_DetachSubscriber_(RxInternalOperatorsOnSubscribeDetach_DetachSubscriber *parent);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeDetach_DetachProducer)

#endif

#if !defined (RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_) && (INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach || defined(INCLUDE_RxInternalOperatorsOnSubscribeDetach_TerminatedProducer))
#define RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_

#define RESTRICT_JavaLangEnum 1
#define INCLUDE_JavaLangEnum 1
#include "java/lang/Enum.h"

#define RESTRICT_RxProducer 1
#define INCLUDE_RxProducer 1
#include "RxProducer.h"

@class IOSObjectArray;

typedef NS_ENUM(NSUInteger, RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_Enum) {
  RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_Enum_INSTANCE = 0,
};

@interface RxInternalOperatorsOnSubscribeDetach_TerminatedProducer : JavaLangEnum < NSCopying, RxProducer >

#pragma mark Public

- (void)requestWithLong:(jlong)n;

+ (RxInternalOperatorsOnSubscribeDetach_TerminatedProducer *)valueOfWithNSString:(NSString *)name;

+ (IOSObjectArray *)values;

#pragma mark Package-Private

- (id)copyWithZone:(NSZone *)zone;

@end

J2OBJC_STATIC_INIT(RxInternalOperatorsOnSubscribeDetach_TerminatedProducer)

/*! INTERNAL ONLY - Use enum accessors declared below. */
FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_TerminatedProducer *RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_values_[];

inline RxInternalOperatorsOnSubscribeDetach_TerminatedProducer *RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_get_INSTANCE();
J2OBJC_ENUM_CONSTANT(RxInternalOperatorsOnSubscribeDetach_TerminatedProducer, INSTANCE)

FOUNDATION_EXPORT IOSObjectArray *RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_values();

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_TerminatedProducer *RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_valueOfWithNSString_(NSString *name);

FOUNDATION_EXPORT RxInternalOperatorsOnSubscribeDetach_TerminatedProducer *RxInternalOperatorsOnSubscribeDetach_TerminatedProducer_fromOrdinal(NSUInteger ordinal);

J2OBJC_TYPE_LITERAL_HEADER(RxInternalOperatorsOnSubscribeDetach_TerminatedProducer)

#endif

#pragma pop_macro("INCLUDE_ALL_RxInternalOperatorsOnSubscribeDetach")
