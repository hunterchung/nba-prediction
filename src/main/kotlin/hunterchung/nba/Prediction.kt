package hunterchung.nba

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum
import hunterchung.nba.data.converter.GameTypeConverter
import hunterchung.nba.data.converter.OffsetDateTimeTypeConverter
import hunterchung.nba.data.converter.TeamTypeConverter
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Data class for a game prediction.
 */
@DynamoDBTable(tableName = Prediction.TABLE_NAME)
data class Prediction(
    @DynamoDBHashKey
    var userId: String,

    @DynamoDBRangeKey
    var gameId: String,

    @DynamoDBTypeConverted(converter = GameTypeConverter::class)
    var game: Game,

    @DynamoDBTypeConverted(converter = TeamTypeConverter::class)
    var team: Team,

    @DynamoDBTypeConverted(converter = OffsetDateTimeTypeConverter::class)
    var timestamp: OffsetDateTime,

    @DynamoDBTypeConvertedEnum
    var result: PredictionResult = PredictionResult.UNDETERMINED
) {
    constructor() : this(
        userId = "",
        gameId = "",
        game = Game(),
        team = Team.PLACEHOLDER,
        timestamp = OffsetDateTime.MIN
    )

    companion object {
        const val TABLE_NAME = "NBA_prediction"
    }

    val theOtherTeam: Team get() = if (game.homeTeam == team) game.visitorTeam else game.homeTeam

    /**
     * Convert the prediction into a speech.
     */
    fun toSpeech(timeZoneId: ZoneId) = """
        ${team.readName} will win against ${theOtherTeam.readName} on
        ${game.startTime.atZoneSameInstant(timeZoneId).format(
        DateTimeFormatter.ofPattern("MMMM dd")
    )}.
    """.trimIndent()
}

/**
 * Prediction result.
 */
enum class PredictionResult {
    WIN, LOSE, UNDETERMINED
}
