package nba

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

object PredictionManager {
    private const val TABLE_NAME = "NBA_prediction"

    private val client = AmazonDynamoDBClientBuilder.defaultClient()
    private val mapper = DynamoDBMapper(client)

//    fun fetchPredictions(user: User, limit: Int = 30) {
//
//        val attributeValueMap = mapOf(":userId" to AttributeValue(user.userId))
//
//        val scanRequest = ScanRequest()
//            .withTableName(TABLE_NAME)
//            .withFilterExpression("userId = :userId")
//            .withExpressionAttributeValues(attributeValueMap)
//            .withLimit(limit)
//
//        val result = client.scan(scanRequest)
//
//        val predictions = result.items
//            .asSequence()
//            .map { Game.from(it) }
//            .toList()
//
//        return predictions
//    }

    fun savePrediction(prediction: Prediction) {
        mapper.save(prediction)
    }
}