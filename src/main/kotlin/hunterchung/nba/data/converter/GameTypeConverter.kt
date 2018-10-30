package hunterchung.nba.data.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import hunterchung.nba.Game
import kotlinx.serialization.json.JSON

/**
 * Convert [Game] to string in DynamoDB.
 */
class GameTypeConverter : DynamoDBTypeConverter<String, Game> {
    override fun unconvert(s: String?): Game =
        JSON.parse(Game.serializer(), s ?: throw IllegalArgumentException("Cannot parse string to Game. $s"))

    override fun convert(game: Game) = JSON.stringify(Game.serializer(), game)
}
