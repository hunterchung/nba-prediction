package hunterchung.nba.data.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import hunterchung.nba.Team

/**
 * Convert [Team] to string in DynamoDB.
 */
class TeamTypeConverter : DynamoDBTypeConverter<String, Team> {
    override fun unconvert(s: String?) = Team.values().first { it.id == s }

    override fun convert(team: Team) = team.id
}
