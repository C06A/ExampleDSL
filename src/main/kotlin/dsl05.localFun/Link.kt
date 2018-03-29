package dsl05.localFun

import java.net.URI
import java.util.*

data class Link(
        val rel: String,
        val href: URI,
        val type: String = "application/hal+json",
        val hreflang: Locale? = null,
        val name: String? = null,
        val title: String? = null,
        val profile: String? = null,
        val deprecation: URI? = null,
        val templated: Boolean? = null
) {
    init {
        require (rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
        require (href.toString().isNotEmpty()) { "URL string must not be empty" }
    }
    private fun toString(name: String, value: Any?): String {
        return when (value) {
            is Unit -> ""
            is String -> "\"$name\":\"$value\""
            is Number -> "\"$name\":$value"
            else -> "\"$name\":\"${value.toString()}\""
        }
    }

    override fun toString(): String {
        return mapOf<String, Any?>(
                "href" to href,
                "type" to type,
                "hreflang" to hreflang,
                "templated" to templated,
                "name" to name,
                "title" to title,
                "profile" to profile,
                "deprecation" to deprecation
        ).filter { (key, value) ->
            value != null
        }.map { (key, value) ->
            if (value != null) toString(key, value) else null
        }.joinToString(",", "\"$rel\":{", "}")
    }
}


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
