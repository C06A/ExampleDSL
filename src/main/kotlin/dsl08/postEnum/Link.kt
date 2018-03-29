package dsl08.postEnum

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
    val HREF = Field.HREF
    val TYPE = Field.TYPE
    val HREFLANG = Field.HREFLANG
    val NAME = Field.NAME
    val TITLE = Field.TITLE
    val PROFILE = Field.PROFILE
    val DEPRECATION = Field.DEPRECATION
    val TEMPLATED = Field.TEMPLATED

    infix fun String.AS(field: Field) {
        when(field) {
            Field.HREF -> href = URI(this)
            Field.TYPE -> type = this
            Field.HREFLANG -> hreflang = Locale(this)
            Field.NAME -> name = this
            Field.TITLE -> title = this
            Field.PROFILE -> profile = this
            Field.DEPRECATION -> deprecation = URI(this)
            Field.TEMPLATED -> templated = this == "true"
        }
    }

    enum class Field {
        HREF, TYPE, HREFLANG, NAME, TITLE, PROFILE, DEPRECATION, TEMPLATED
    }

    infix fun Boolean.AS(field: Field) {
        when(field) {
            Field.TEMPLATED -> templated = this
            else -> throw InvalidParameterException("Only 'templated' can be Boolean")
        }
    }

    infix fun URI.AS(field: Field) {
        when(field) {
            Field.HREF -> href = this
            Field.DEPRECATION -> deprecation = this
            else -> throw InvalidParameterException("Only 'href' and 'deprecation' can be URI")
        }
    }

    infix fun Locale.AS(field: Field) {
        when(field) {
            Field.HREFLANG -> hreflang = this
            else -> throw InvalidParameterException("Only 'hreflang' can be Locale")
        }
    }
}
