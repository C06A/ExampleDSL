package dsl03.TempData

import dsl01.Naive.Link
import java.net.URI
import java.util.*

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
