package hunterchung.extensions

import com.amazon.ask.model.interfaces.display.RenderTemplateDirective
import com.amazon.ask.model.interfaces.display.Template
import com.amazon.ask.response.ResponseBuilder

fun ResponseBuilder.addTemplate(template: Template): ResponseBuilder =
    addDirective(RenderTemplateDirective.builder().withTemplate(template).build())