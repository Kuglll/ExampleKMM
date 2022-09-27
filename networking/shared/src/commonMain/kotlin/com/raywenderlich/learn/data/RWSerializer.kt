package com.raywenderlich.learn.data

import com.raywenderlich.learn.data.model.PLATFORM
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

private fun findByKey(key: String, default: PLATFORM = PLATFORM.ALL): PLATFORM {
    return PLATFORM.values().find { it.value == key } ?: default
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PLATFORM::class)
object RWSerializer : KSerializer<PLATFORM> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("PLATFORM", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PLATFORM) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): PLATFORM {
        return try {
            val key = decoder.decodeString()
            findByKey(key)
        } catch (e: IllegalArgumentException) {
            PLATFORM.ALL
        }
    }
}


