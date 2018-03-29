package dsl02.Function

import java.net.URI
import java.util.*

data class Link(
        val rel: String,
        val href: URI,
        var type: String = "application/hal+json",
        var hreflang: Locale? = null,
        var name: String? = null,
        var title: String? = null,
        var profile: String? = null,
        var deprecation: URI? = null,
        var templated: Boolean? = null
) {
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

fun link(rel: String, href: URI, closure: Link.() -> Unit = {}): Link {
    require(rel.isNotEmpty()) { "Relation cannot be empty string." }
    require(href.toString().isNotEmpty()) { "URL string must not be empty" }

    val link = Link(rel, href)
    link.closure()
    return link
}
