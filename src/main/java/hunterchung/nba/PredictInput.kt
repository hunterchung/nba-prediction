package hunterchung.nba

import com.amazon.ask.model.IntentRequest
import hunterchung.extensions.getTopResolutionValue
import java.time.LocalDate

data class PredictInput(val team: Team, val date: LocalDate) {
    companion object {
        private const val SLOT_DATE = "date"
        private const val SLOT_TEAM = "team"

        fun from(request: IntentRequest): PredictInput {
            val teamId = request.intent.slots[SLOT_TEAM]?.getTopResolutionValue()?.id
                ?: throw IllegalArgumentException("Cannot find team slot in request.")
            val dateString = request.intent.slots[SLOT_DATE]?.value
                ?: throw IllegalArgumentException("Cannot find date slot in request.")

            return PredictInput(
                Team.valueOf(teamId),
                LocalDate.parse(dateString)
            )
        }
    }
}