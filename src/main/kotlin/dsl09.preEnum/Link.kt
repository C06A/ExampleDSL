package dsl09.preEnum

import dsl01.Naive.Link
import java.net.URI
import java.security.InvalidParameterException
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
        return Link( rel, href, type, hreflang, nameStr, title, profile, deprecation, templated)
    }
}

data class TempLink(var rel: String,
                    var href: URI = URI(""),
                    var type: String = "application/hal+json",
                    var hreflang: Locale? = null,
                    var nameStr: String? = null,
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

    infix fun Field.IS(value: String) {
        when(this) {
            Field.HREF -> href = URI(value)
            Field.TYPE -> type = value
            Field.HREFLANG -> hreflang = Locale(value)
            Field.NAME -> nameStr = value
            Field.TITLE -> title = value
            Field.PROFILE -> profile = value
            Field.DEPRECATION -> deprecation = URI(value)
            Field.TEMPLATED -> templated = value == "true"
        }
    }

    enum class Field {
        HREF, TYPE, HREFLANG, NAME, TITLE, PROFILE, DEPRECATION, TEMPLATED
    }

    infix fun Field.IS(value: Boolean) {
        when(this) {
            Field.TEMPLATED -> templated = value
            else -> throw InvalidParameterException("Only 'templated' can be Boolean")
        }
    }

    infix fun Field.IS(value: URI) {
        when(this) {
            Field.HREF -> href = value
            Field.DEPRECATION -> deprecation = value
            else -> throw InvalidParameterException("Only 'href' and 'deprecation' can be URI")
        }
    }

    infix fun Field.IS(value: Locale) {
        when(this) {
            Field.HREFLANG -> hreflang = value
            else -> throw InvalidParameterException("Only 'hreflang' can be Locale")
        }
    }
}
