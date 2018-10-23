package hunterchung.template

import com.amazon.ask.model.interfaces.display.ListItem
import com.amazon.ask.model.interfaces.display.ListTemplate1
import com.amazon.ask.model.interfaces.display.PlainText
import com.amazon.ask.model.interfaces.display.TextContent
import com.amazon.ask.model.interfaces.display.TextField
import hunterchung.nba.Prediction
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * A collection of helper functions for [ListTemplate1].
 */
object PredictionTemplate {
    /**
     * Parse a list of predictions to [ListTemplate1].
     */
    fun from(title: String, predictions: List<Prediction>, zoneId: ZoneId): ListTemplate1 {
        val listItems = predictions.map {
            val textContent = TextContent.builder()
                .withPrimaryText(getPrimaryText(it))
                .withSecondaryText(getSecondaryText(it, zoneId))
                .build()
            ListItem.builder().withTextContent(textContent).build()
        }

        return ListTemplate1.builder()
            .withTitle(title)
            .withListItems(listItems)
            .build()
    }

    private fun getPrimaryText(prediction: Prediction): TextField {
        val text = "${prediction.team.teamName} Win"
        return PlainText.builder().withText(text).build()
    }

    private fun getSecondaryText(prediction: Prediction, zoneId: ZoneId): TextField {
        val gameInstant = prediction.game.startTime.toInstant()
        val dateString = ZonedDateTime.ofInstant(gameInstant, zoneId).format(DateTimeFormatter.ofPattern("MMM dd"))
        val text = "against ${prediction.theOtherTeam.teamName} on $dateString"

        return PlainText.builder().withText(text).build()
    }
}
