package dsl05.localFun

import dsl01.Naive.Link
import java.net.URI
import java.util.*

fun String.link(href: URI = URI(""), closure: TempLink.() -> Unit = {}): Link {
    val temp = TempLink(this, href)
    temp.closure()
    return temp.toLink()
}

data class TempLink(private var rel: String = "", private var href: URI = URI("")) {
    private var type: String = "application/hal+json"
    private var hreflang: Locale? = null
    private var name: String? = null
    private var title: String? = null
    private var profile: String? = null
    private var deprecation: URI? = null
    private var templated: Boolean? = null

    fun String.rel() { rel = this }
    fun URI.href() { href = this }
    fun String.type() { type = this }
    fun Locale.hreflang() { hreflang = this }
    fun String.name() { name = this }
    fun String.title() { title = this }
    fun String.profile() { profile = this }
    fun URI.deprecation() { deprecation = this }
    fun Boolean.templated() { templated = this }

    fun String.hreflang() {
        hreflang = Locale.forLanguageTag(this)
    }

    fun String.href() { href = URI(this) }
    fun String.deprecation() { deprecation = URI(this) }

    fun toLink(): Link {
        return Link(rel, href, type, hreflang, name, title, profile, deprecation, templated)
    }
}
