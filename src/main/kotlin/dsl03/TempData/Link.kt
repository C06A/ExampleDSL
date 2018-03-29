package dsl03.TempData

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


fun link(rel: String = "", href: URI = URI(""), closure: TempLink.() -> Unit = {}): Link {
    val temp = TempLink(rel, href)
    temp.closure()
    val link = Link(
            temp.rel ?: ""
            , temp.href ?: URI("")
            , temp.type
            , temp.hreflang
            , temp.name
            , temp.title
            , temp.profile
            , temp.deprecation
            , temp.templated
    )
    return link
}

data class TempLink(var rel: String? = "",
    var href: URI? = URI(""),
    var type: String = "application/hal+json",
    var hreflang: Locale? = null,
    var name: String? = null,
    var title: String? = null,
    var profile: String? = null,
    var deprecation: URI? = null,
    var templated: Boolean? = null) {
}
