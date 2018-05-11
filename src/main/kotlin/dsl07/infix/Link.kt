package dsl07.infix

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

infix fun TempLink.href(href: String): TempLink {
    this.href = URI(href)
    return this
}
infix fun TempLink.href(href: URI): TempLink {
    this.href = href
    return this
}

infix fun TempLink.type(type: String): TempLink {
    this.type = type
    return this
}

infix fun TempLink.hreflang(hreflang: Locale): TempLink {
    this.hreflang = hreflang
    return this
}
infix fun TempLink.hreflang(hreflang: String): TempLink {
    this.hreflang = Locale(hreflang)
    return this
}

infix fun TempLink.name(name: String): TempLink {
    this.name = name
    return this
}

infix fun TempLink.title(title: String): TempLink {
    this.title = title
    return this
}

infix fun TempLink.profile(profile: String): TempLink {
    this.profile = profile
    return this
}

infix fun TempLink.deprecation(deprecation: String): TempLink {
    this.deprecation = URI(deprecation)
    return this
}
infix fun TempLink.deprecation(deprecation: URI): TempLink {
    this.deprecation = deprecation
    return this
}

infix fun TempLink.templated(templated: Boolean): TempLink {
    this.templated = templated
    return this
}
fun TempLink.templated(): TempLink {
    this.templated = true
    return this
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
        require (rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
    }
}
