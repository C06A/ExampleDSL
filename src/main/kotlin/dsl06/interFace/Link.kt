package dsl06.interFace

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
