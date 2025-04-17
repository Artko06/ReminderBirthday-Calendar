package com.example.data.local.util.serializer

import android.util.Base64
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ByteArraySerializer: KSerializer<ByteArray> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            serialName = "ByteArray",
            kind = PrimitiveKind.STRING
        )

    override fun serialize(encoder: Encoder, value: ByteArray) {
        val strBase64 = Base64.encodeToString(value, Base64.DEFAULT)
        encoder.encodeString(value = strBase64)
    }

    override fun deserialize(decoder: Decoder): ByteArray {
        val strBase64 = decoder.decodeString()
        return Base64.decode(strBase64, Base64.DEFAULT)
    }
}