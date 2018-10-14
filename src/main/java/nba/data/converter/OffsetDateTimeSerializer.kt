package nba.data.converter

import kotlinx.serialization.KInput
import kotlinx.serialization.KOutput
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.time.OffsetDateTime

@Serializer(forClass = OffsetDateTime::class)
class OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override fun load(input: KInput): OffsetDateTime = OffsetDateTime.parse(input.readStringValue())

    override fun save(output: KOutput, obj: OffsetDateTime) = output.writeStringValue(obj.toString())
}