package nba

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import kotlinx.serialization.Serializable
import nba.data.converter.OffsetDateTimeSerializer
import nba.data.converter.OffsetDateTimeTypeConverter
import nba.data.converter.TeamTypeConverter
import java.time.OffsetDateTime

@Serializable
@DynamoDBTable(tableName = "NBA_schedule")
data class Game(
    @DynamoDBHashKey(attributeName = "gameId")
    var id: String,

    @DynamoDBAttribute(attributeName = "gameUrlCode")
    var urlCode: String,

    @DynamoDBTypeConverted(converter = TeamTypeConverter::class)
    @DynamoDBAttribute(attributeName = "hTeamId")
    var homeTeam: Team,

    @DynamoDBTypeConverted(converter = TeamTypeConverter::class)
    @DynamoDBAttribute(attributeName = "vTeamId")
    var visitorTeam: Team,

    @DynamoDBTypeConverted(converter = OffsetDateTimeTypeConverter::class)
    @Serializable(with = OffsetDateTimeSerializer::class)
    var startTime: OffsetDateTime
) {
    constructor() : this(
        id = "",
        urlCode = "",
        homeTeam = Team.PLACEHOLDER,
        visitorTeam = Team.PLACEHOLDER,
        startTime = OffsetDateTime.MIN
    )
}