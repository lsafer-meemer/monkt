/*
 *	Copyright 2022 cufy.org
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.codec

import org.cufy.bson.*
import org.cufy.codec.*
import java.math.BigDecimal
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/* ============= ------------------ ============= */

/**
 * A bson variant of [FieldCodec] enabling extra
 * features that can be achieved only when the
 * target output is known to be bson.
 *
 * This implements [MutableBsonDocumentField] to enable the following syntax:
 * ```kotlin
 * document {
 *      MyField by myValue
 * }
 * ```
 *
 * This interface will be useless after context
 * receivers is released for production.
 * This interface will be removed gradually after
 * context receivers is released for production.
 *
 * @param I the type of the decoded value.
 * @param O the type of the encoded value.
 * @author LSafer
 * @since 2.0.0
 */
interface BsonFieldCodec<I, O : BsonElement> : FieldCodec<I, O>, MutableBsonDocumentField<I> {
    override fun encode(value: I): BsonElement =
        encode(value, this)
}

// Constructor

/**
 * Create a new [BsonFieldCodec] with
 * the given [name] and backed by the given [codec].
 */
@ExperimentalCodecApi
fun <I, O : BsonElement> BsonFieldCodec(name: String, codec: Codec<I, O>): BsonFieldCodec<I, O> {
    return object : BsonFieldCodec<I, O>, Codec<I, O> by codec {
        override val name = name
    }
}

/* ============= ------------------ ============= */

/**
 * Get the value of the field with the name of the
 * given [codec] and decode it using the given [codec].
 */
operator fun <I, O : BsonElement> BsonDocument.get(codec: FieldCodec<I, O>): I {
    return decodeAny(this[codec.name] ?: BsonUndefined, codec)
}

/**
 * Decode this document to [I] using the given [codec].
 */
@CodecMarker
infix fun <I> BsonDocument.decode(codec: Codec<I, BsonDocument>): I {
    return decode(this, codec)
}

/**
 * Encode this instance to a [BsonDocument] using the given [codec].
 */
@CodecMarker
infix fun <I> I.encode(codec: Codec<I, BsonDocument>): BsonDocument {
    return encode(this, codec)
}

/* ============= ------------------ ============= */

/**
 * A codec that always decodes nullish values
 * to `null` and encodes `null` to [BsonNull] and
 * uses the given [codec] otherwise.
 *
 * Nullish values includes [BsonNull] and [BsonUndefined]
 *
 * @author LSafer
 * @since 2.0.0
 */
class BsonNullableCodec<I, O : BsonElement>(
    @Suppress("MemberVisibilityCanBePrivate")
    val codec: Codec<I, O>
) : Codec<I?, BsonElement> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        when (value) {
            null -> success(BsonNull)
            else -> codec.encode(value)
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        when (value) {
            BsonNull, BsonUndefined -> success(null)
            else -> codec.decode(value)
        }
}

/**
 * Obtain a codec that always decodes nullish
 * values to `null` and encodes `null`
 * to [BsonNull] and uses this codec otherwise.
 *
 * Nullish values includes [BsonNull] and [BsonUndefined]
 */
val <I, O : BsonElement> Codec<I, O>.Nullable: BsonNullableCodec<I, O>
    get() = BsonNullableCodec(this)

/* ============= ------------------ ============= */

/**
 * A codec for [List] and [BsonArray] that uses
 * the given [codec] to encode/decode each
 * individual item.
 */
class BsonArrayCodec<I, O : BsonElement>(
    @Suppress("MemberVisibilityCanBePrivate")
    val codec: Codec<I, O>
) : Codec<List<I>, BsonArray> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: List<*> ->
            success(BsonArray(it.map {
                encodeAny(it, codec)
            }))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonArray ->
            success(it.map {
                decodeAny(it, codec)
            })
        }
}

/**
 * Obtain a codec for [List] and [BsonArray] that
 * uses this codec to encode/decode each
 * individual item.
 */
val <I, O : BsonElement> Codec<I, O>.Array: BsonArrayCodec<I, O>
    get() = BsonArrayCodec(this)

/* ============= ------------------ ============= */

/**
 * The codec for [String] and [BsonString].
 *
 * @since 2.0.0
 */
object BsonStringCodec : Codec<String, BsonString> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: String ->
            success(BsonString(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonString ->
            success(it.value)
        }
}

/**
 * The codec for [String] and [BsonString].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.String get() = BsonStringCodec

/* ============= ------------------ ============= */

/**
 * The codec for [Boolean] and [BsonBoolean].
 *
 * @since 2.0.0
 */
object BsonBooleanCodec : Codec<Boolean, BsonBoolean> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Boolean ->
            success(BsonBoolean(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonBoolean ->
            success(it.value)
        }
}

/**
 * The codec for [Boolean] and [BsonBoolean].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Boolean get() = BsonBooleanCodec

/* ============= ------------------ ============= */

/**
 * The codec for [Int] and [BsonInt32].
 *
 * @since 2.0.0
 */
object BsonInt32Codec : Codec<Int, BsonInt32> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Int ->
            success(BsonInt32(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonInt32 ->
            success(it.value)
        }
}

/**
 * The codec for [Int] and [BsonInt32].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Int32 get() = BsonInt32Codec

/* ============= ------------------ ============= */

/**
 * The codec for [Long] and [BsonInt64].
 *
 * @since 2.0.0
 */
object BsonInt64Codec : Codec<Long, BsonInt64> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Long ->
            success(BsonInt64(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonInt64 ->
            success(it.value)
        }
}

/**
 * The codec for [Long] and [BsonInt64].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Int64 get() = BsonInt64Codec

/* ============= ------------------ ============= */

/**
 * The codec for [Double] and [BsonDouble].
 *
 * @since 2.0.0
 */
object BsonDoubleCodec : Codec<Double, BsonDouble> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Double ->
            success(BsonDouble(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonDouble ->
            success(it.value)
        }
}

/**
 * The codec for [Double] and [BsonDouble].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Double get() = BsonDoubleCodec

/* ============= ------------------ ============= */

/**
 * The codec for [Decimal128] and [BsonDecimal128].
 *
 * @since 2.0.0
 */
object BsonDecimal128Codec : Codec<Decimal128, BsonDecimal128> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Decimal128 ->
            success(BsonDecimal128(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonDecimal128 ->
            success(it.value)
        }
}

/**
 * The codec for [Decimal128] and [BsonDecimal128].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Decimal128 get() = BsonDecimal128Codec

/* ============= ------------------ ============= */

/**
 * The codec for [BigDecimal] and [BsonDecimal128].
 *
 * @since 2.0.0
 */
object BigDecimalToBsonCodec : Codec<BigDecimal, BsonDecimal128> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: BigDecimal ->
            success(BsonDecimal128(Decimal128(it)))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonDecimal128 ->
            success(it.value.bigDecimalValue())
        }
}

/**
 * The codec for [BigDecimal] and [BsonDecimal128].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.BigDecimal get() = BigDecimalToBsonCodec

/* ============= ------------------ ============= */

/**
 * The codec for [ObjectId] and [BsonObjectId].
 *
 * @since 2.0.0
 */
object BsonObjectIdCodec : Codec<ObjectId, BsonObjectId> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: ObjectId ->
            success(BsonObjectId(it))
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonObjectId ->
            success(it.value)
        }
}

/**
 * The codec for [ObjectId] and [BsonObjectId].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.ObjectId get() = BsonObjectIdCodec

/* ============= ------------------ ============= */

/**
 * The codec for [Id] and [BsonObjectId] or [BsonString].
 *
 * @since 2.0.0
 */
object IdToBsonCodec : Codec<Id<*>, BsonElement> {
    @AdvancedCodecApi
    override fun encode(value: Any?) =
        tryInlineCodec(value) { it: Id<*> ->
            success(it.b)
        }

    @AdvancedCodecApi
    override fun decode(value: Any?) =
        tryInlineCodec(value) { it: BsonElement ->
            when (it) {
                is BsonObjectId -> success(Id<Any>(it.value))
                is BsonString -> success(Id(it.value))
                else -> failure(CodecException(
                    "Cannot decode ${it::class}; expected either " +
                            BsonObjectId::class + " or " +
                            BsonString::class
                ))
            }
        }
}

/**
 * The codec for [Id] and [BsonObjectId] or [BsonString].
 *
 * @since 2.0.0
 */
@Suppress("UnusedReceiverParameter")
val Codecs.Id get() = IdToBsonCodec

/**
 * The codec for [Id] and [BsonObjectId] or [BsonString].
 *
 * @param T the type of the id.
 * @since 2.0.0
 */
@Suppress("UNCHECKED_CAST", "FunctionName")
fun <T> Codecs.Id() = Id as Codec<Id<T>, BsonElement>

/* ============= ------------------ ============= */