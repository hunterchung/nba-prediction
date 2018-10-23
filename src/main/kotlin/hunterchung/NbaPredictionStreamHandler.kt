package hunterchung

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import hunterchung.handler.MyPredictionHandler
import hunterchung.handler.PredictIntentHandler

/**
 * Handle all requests.
 */
class NbaPredictionStreamHandler : SkillStreamHandler(NbaPredictionStreamHandler.skill) {
    companion object {
        private val skill = Skills.standard()
            .addRequestHandlers(
                PredictIntentHandler(),
                MyPredictionHandler()
//                CancelandStopIntentHandler(),
//                HelpIntentHandler(),
//                LaunchRequestHandler(),
//                SessionEndedRequestHandler(),
//                FallbackIntentHandler()
            )
            .build()
    }
}
