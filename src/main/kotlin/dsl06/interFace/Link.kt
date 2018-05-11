package dsl06.interFace

import dsl01.Naive.Link
import java.net.URI
import java.util.*

fun link(rel: String = "", href: URI = URI(""), closure: (LinkBuilder.() -> Unit)? = null): Link {
    val builder = object: LinkBuilder {
        override var rel = rel
        override var href: URI = href
        override var type: String = "application/hal+json"
        override var hreflang: Locale? = null
        override var name: String? = null
        override var title: String? = null
        override var profile: String? = null
        override var deprecation: URI? = null
        override var templated: Boolean? = null
    }

    closure?.invoke(builder)

    return with(builder) {
        Link(builder.rel, builder.href, type, hreflang, name, title, profile, deprecation, templated)
    }
}

interface LinkBuilder {
    var rel: String
    var href: URI
    var type: String
    var hreflang: Locale?
    var name: String?
    var title: String?
    var profile: String?
    var deprecation: URI?
    var templated: Boolean?
}
