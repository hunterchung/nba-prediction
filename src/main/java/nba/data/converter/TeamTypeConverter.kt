package nba.data.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import nba.Team

class TeamTypeConverter : DynamoDBTypeConverter<String, Team> {
    override fun unconvert(s: String?) = Team.values().first { it.id == s }

    override fun convert(team: Team) = team.id
}