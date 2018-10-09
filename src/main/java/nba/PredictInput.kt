package nba

import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Slot
import java.lang.IllegalArgumentException
import java.time.LocalDate

data class PredictInput(val team: Team, val date: LocalDate) {
    companion object {
        private const val SLOT_DATE = "date"
        private const val SLOT_TEAM = "team"

        fun from(request: IntentRequest): PredictInput {
            val teamSlot =
                request.intent.slots[SLOT_TEAM] ?: throw IllegalArgumentException("Cannot find team slot in request.")
            val dateSlot =
                request.intent.slots[SLOT_DATE] ?: throw IllegalArgumentException("Cannot find date slot in request.")

            return PredictInput(
                getTopTeamSlotValue(teamSlot),
                LocalDate.parse(dateSlot.value)
            )
        }

        private fun getTopTeamSlotValue(slot: Slot): Team {
            val teamId = slot.resolutions?.resolutionsPerAuthority?.firstOrNull()?.values?.firstOrNull()?.value?.id
                ?: throw java.lang.IllegalArgumentException("Cannot find team id from team slot.")

            return Team.valueOf(teamId)
        }
    }
}