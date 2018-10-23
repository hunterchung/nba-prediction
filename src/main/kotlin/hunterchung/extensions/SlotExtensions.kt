package hunterchung.extensions

import com.amazon.ask.model.Slot

/**
 * Get the first resolution value from [Slot].
 */
fun Slot.getTopResolutionValue() = resolutions?.resolutionsPerAuthority?.firstOrNull()?.values?.firstOrNull()?.value
