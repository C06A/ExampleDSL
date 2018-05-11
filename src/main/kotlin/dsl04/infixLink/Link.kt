package dsl04.infixLink

import dsl01.Naive.Link
import java.net.URI
import java.util.*

infix fun String.link(href: URI): Link {
    return Link(this, href)
}

infix fun Link.with(closure: TempLink.() -> Unit): Link {
    val temp = TempLink(rel, href, type, hreflang, name, title, profile, deprecation, templated)
    temp.closure()
    return temp.toLink()
}

data class TempLink(var rel: String = "",
                    var href: URI = URI(""),
                    var type: String = "application/hal+json",
                    var hreflang: Locale? = null,
                    var name: String? = null,
                    var title: String? = null,
                    var profile: String? = null,
                    var deprecation: URI? = null,
                    var templated: Boolean? = null) {
    fun toLink(): Link {
        return Link( rel, href, type, hreflang, name, title, profile, deprecation, templated)
    }
}
