package helloworld.extensions

import com.amazon.ask.model.Slot

fun Slot.getTopResolutionValue() = resolutions?.resolutionsPerAuthority?.firstOrNull()?.values?.firstOrNull()?.value