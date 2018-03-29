package dsl10.postClosure

import java.net.URI
import java.security.InvalidParameterException
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
        require (rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
    }
    val HREF = { value: String -> href = URI(value) }
    val TYPE = { value: String -> type = value }
    val HREFLANG = { value: String -> hreflang = Locale(value) }
    val NAME = { value: String -> name = value }
    val TITLE = { value: String -> title = value }
    val PROFILE = { value: String -> profile = value }
    val DEPRECATION = { value: String -> deprecation = URI(value) }
    val TEMPLATED = { value: String -> templated = value == "true" }

    infix fun String.AS(init: (String) -> Unit) {
        init(this)
    }

//    infix fun Boolean.AS(init: (Boolean) -> Unit) {
//        init(this)
//    }
//
//    infix fun URI.AS(init: (URI) -> Unit) {
//        init(this)
//    }
//
//    infix fun Locale.AS(init: (Locale) -> Unit) {
//        init(this)
//    }
}
