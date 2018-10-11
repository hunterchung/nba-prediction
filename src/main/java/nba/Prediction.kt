package nba

import com.amazonaws.services.dynamodbv2.datamodeling.*
import helloworld.common.OffsetDateTimeConverter
import nba.dynamoDBConverter.GameTypeConverter
import java.time.OffsetDateTime

@DynamoDBTable(tableName = "NBA_prediction")
class Prediction() {
    @DynamoDBHashKey
    lateinit var userId: String

    @DynamoDBTypeConverted(converter = GameTypeConverter::class)
    lateinit var game: Game

    @DynamoDBTypeConvertedEnum
    lateinit var team: Team

    @DynamoDBTypeConverted(converter = OffsetDateTimeConverter::class)
    @DynamoDBRangeKey
    lateinit var timestamp: OffsetDateTime

    @DynamoDBTypeConvertedEnum
    var result: PredictionResult = PredictionResult.UNDETERMINED

    constructor(
        userId: String,
        game: Game,
        team: Team,
        timestamp: OffsetDateTime,
        result: PredictionResult = PredictionResult.UNDETERMINED
    ) : this() {
        this.userId = userId
        this.game = game
        this.team = team
        this.timestamp = timestamp
        this.result = result
    }
}

enum class PredictionResult {
    WIN, LOSE, UNDETERMINED
}