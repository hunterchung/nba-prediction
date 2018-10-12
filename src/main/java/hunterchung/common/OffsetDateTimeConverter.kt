package hunterchung.common

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.OffsetDateTime

class OffsetDateTimeConverter : DynamoDBTypeConverter<String, OffsetDateTime> {
    override fun unconvert(s: String?): OffsetDateTime = OffsetDateTime.parse(s)

    override fun convert(dateTime: OffsetDateTime) = dateTime.toString()
}