package hunterchung

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import hunterchung.handler.PredictIntentHandler


class NbaPredictionStreamHandler : SkillStreamHandler(NbaPredictionStreamHandler.skill) {
    companion object {
        private val skill = Skills.standard()
            .addRequestHandlers(
//                CancelandStopIntentHandler(),
                PredictIntentHandler()
//                HelpIntentHandler(),
//                LaunchRequestHandler(),
//                SessionEndedRequestHandler(),
//                FallbackIntentHandler()
            )
            .build()
    }
}