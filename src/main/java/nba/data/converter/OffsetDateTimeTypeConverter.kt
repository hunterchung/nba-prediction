package nba.data.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.OffsetDateTime

class OffsetDateTimeTypeConverter : DynamoDBTypeConverter<String, OffsetDateTime> {
    override fun unconvert(s: String?): OffsetDateTime = OffsetDateTime.parse(s)

    override fun convert(datetime: OffsetDateTime) = datetime.toString()
}