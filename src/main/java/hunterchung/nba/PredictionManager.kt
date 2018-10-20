package hunterchung.nba

import com.amazon.ask.model.User
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import hunterchung.nba.data.DynamoDBUtil

object PredictionManager {

    fun fetchPrediction(user: User, limit: Int = 10): List<Prediction> {
        val attributeValueMap = mapOf(
            ":userId" to AttributeValue(user.userId)
        )

        val expression = DynamoDBQueryExpression<Prediction>()
            .withKeyConditionExpression("userId = :userId")
            .withExpressionAttributeValues(attributeValueMap)
            .withLimit(limit)
            .withScanIndexForward(false)

        return DynamoDBUtil.mapper.query(Prediction::class.java, expression)
    }

    fun savePrediction(prediction: Prediction) = DynamoDBUtil.mapper.save(prediction)
}