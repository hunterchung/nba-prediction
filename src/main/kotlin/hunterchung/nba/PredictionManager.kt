package hunterchung.nba

import com.amazon.ask.model.User
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import hunterchung.nba.data.DynamoDBUtil

/**
 * Singleton class to manage prediction persistence.
 */
object PredictionManager {
    const val PREDICTION_FETCH_LIMIT = 10

    /**
     * Fetch predictions by [user].
     */
    fun fetchPredictions(user: User, limit: Int = PREDICTION_FETCH_LIMIT): List<Prediction> {
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

    /**
     * Save [prediction] into DB.
     */
    fun savePrediction(prediction: Prediction) = DynamoDBUtil.mapper.save(prediction)
}
