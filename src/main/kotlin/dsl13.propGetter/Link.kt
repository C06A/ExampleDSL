package dsl13.propGetter

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
    println("rel:${temp.rel}, href:${temp.href}")
    return with(temp) {
        return Link( rel, href, type, hreflang, name, title, profile, deprecation, templated)
    }
}

data class TempLink(var rel: String) {
    init {
        require(rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
    }

    val parent = this

    var href: URI = URI("")
    val String.href: String
        get() {
            parent.href = URI(this); return this
        }
    val URI.href: URI
        get() {
            parent.href = this; return this
        }

    var type: String = "application/hal+json"
    val String.type: String
        get() {
            parent.type = this; return this
        }

    var hreflang: Locale? = null
    val String.hreflang: String
        get() {
            parent.hreflang = Locale(this); return this
        }
    val Locale.hreflang: Locale
        get() {
            parent.hreflang = this; return this
        }

    var name: String? = null
    val String.name: String
        get() {
            parent.name = this; return this
        }

    var title: String? = null
    val String.title: String
        get() {
            parent.title = this; return this
        }

    var profile: String? = null
    val String.profile: String
        get() {
            parent.profile = this; return this
        }

    var deprecation: URI? = null
    val String.deprecation: String
        get() {
            parent.deprecation = URI(this); return this
        }
    val URI.deprecation: URI
        get() {
            parent.deprecation = this; return this
        }

    var templated: Boolean? = null
    val String.templated: String
        get() {
            parent.templated = this == "true"; return this
        }
    val Boolean.templated: Boolean
        get() {
            parent.templated = this; return this
        }
}
