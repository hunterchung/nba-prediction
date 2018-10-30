package hunterchung.nba.data.converter

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.StringDescriptor
import java.time.OffsetDateTime

/**
 * Custom serialization for [OffsetDateTime].
 */
@Serializer(forClass = OffsetDateTime::class)
class OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun deserialize(input: Decoder): OffsetDateTime = OffsetDateTime.parse(input.decodeString())

    override fun serialize(output: Encoder, obj: OffsetDateTime) = output.encodeString(obj.toString())
}
