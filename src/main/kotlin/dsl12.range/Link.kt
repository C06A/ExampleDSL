package dsl12.range

import dsl01.Naive.Link
import java.net.URI
import java.util.*

infix fun String.link(href: String): Link {
    return Link( this, URI(href))
}
infix fun String.link(href: URI): Link {
    return Link( this, href)
}
infix fun String.link(init: TempLink.() -> Unit): Link {
    val temp = TempLink(this)
    temp.init()
    return with(temp) {
        return Link( rel, href, type, hreflang, name, title, profile, deprecation, templated)
    }
}

data class TempLink(var rel: String,
                    var href: URI = URI(""),
                    var type: String = "application/hal+json",
                    var hreflang: Locale? = null,
                    var name: String? = null,
                    var title: String? = null,
                    var profile: String? = null,
                    var deprecation: URI? = null,
                    var templated: Boolean? = null) {
    init {
        require(rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
    }

    val HREF = { value: String -> href = URI(value) }
    val TYPE = { value: String -> type = value }
    val HREFLANG = { value: String -> hreflang = Locale(value) }
    val NAME = { value: String -> name = value }
    val TITLE = { value: String -> title = value }
    val PROFILE = { value: String -> profile = value }
    val DEPRECATION = { value: String -> deprecation = URI(value) }
    val TEMPLATED = { value: String -> templated = value == "true" }

    operator fun ((String) -> Unit).rangeTo(value: String) {
        this(value)
    }
}
