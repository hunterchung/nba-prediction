package helloworld.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import nba.PredictInput
import nba.Prediction
import nba.ScheduleManager
import nba.Team
import nba.data.DynamoDBUtil
import org.apache.logging.log4j.LogManager
import java.time.LocalDate
import java.util.*

class PredictIntentHandler : RequestHandler {
    companion object {
        private val logger = LogManager.getLogger(PredictIntentHandler::class.java)
    }

    override fun canHandle(input: HandlerInput) = input.matches(intentName("predict"))

    override fun handle(input: HandlerInput): Optional<Response> {
        val intentRequest =
            input.request as? IntentRequest ?: throw IllegalArgumentException("Cannot Parse the request.")
        val predictInput = PredictInput.from(intentRequest)
        val game = ScheduleManager.fetchGame(predictInput.date, predictInput.team)

        print("game: $game")
        logger.error("hunter test game: $game")
        val prediction =
            Prediction(input.requestEnvelope.session.user.userId, game!!, predictInput.team, intentRequest.timestamp)
        DynamoDBUtil.mapper.save(prediction)

        return input.responseBuilder
            .withSpeech("")
            .withSimpleCard("HelloWorld", "")
            .build()
    }


    private fun getSpeechText(team: Team, date: LocalDate) = "There is a ${team.name} game on $date"

    private fun getFailedSpeechText(team: Team, date: LocalDate) = "Cannot find ${team.name} game on $date"
}