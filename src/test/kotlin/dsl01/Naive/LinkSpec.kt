package dsl01.Naive

import com.helpchoice.kotline.hal.InvalidParametersException
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldStartWith
import org.amshove.kluent.shouldThrow
import java.net.URI
import java.util.*

class LinkSpec: StringSpec() {
    init {
        val linkList = listOf<Link>(
                Link("relation", URI("address")),
                Link("relation",
                        URI("address"),
                        "image/*",
                        Locale.CHINESE,
                        "test name",
                        "test title",
                        "test profile",
                        URI("deprication+info"),
                        true
                ),
                Link("relation",
                        URI("address"),
                        templated = true,
                        deprecation = URI("deprecation+info"),
                        profile = "test profile",
                        title = "test title",
                        name = "test name",
                        hreflang = Locale.CHINESE,
                        type = "image/*"
                )
        )
        val linkMin = linkList[0]
        val linkMax = linkList[1]
        val linkByNames = linkList[2]

        "link with minimal set" {
            val link = linkMin

            link.rel shouldBe "relation"
            link.href.toString() shouldBe "address"
            link.type shouldBe "application/hal+json"
            link.hreflang shouldBe null
            link.name shouldBe null
            link.title shouldBe null
            link.profile shouldBe null
            link.deprecation shouldBe null
            link.templated shouldBe null
        }

        "link with whole set by order" {
            val link = linkMax

            link.rel shouldBe "relation"
            link.href.toString() shouldBe "address"
            link.type shouldBe "image/*"
            link.hreflang shouldBe Locale.CHINESE
            link.name shouldBe "test name"
            link.title shouldBe "test title"
            link.profile shouldBe "test profile"
            link.deprecation.toString() shouldBe "deprication+info"
            link.templated shouldBe true
        }

        "link with whole set by names" {
            val link = linkByNames

            link.rel shouldBe "relation"
            link.href.toString() shouldBe "address"
            link.type shouldBe "image/*"
            link.hreflang shouldBe Locale.CHINESE
            link.name shouldBe "test name"
            link.title shouldBe "test title"
            link.profile shouldBe "test profile"
            link.deprecation.toString() shouldBe "deprecation+info"
            link.templated shouldBe true
        }

        "minimal Link.toString()" {
            val link = linkMin

            link.toString() shouldBe listOf<String>(
                    "\"relation\":{",
                    "\"href\":\"address\",",
                    "\"type\":\"application/hal+json\"",
                    "}").joinToString("")

        }

        "Link.toString()" {
            val link = linkByNames

            link.toString() shouldBe listOf<String>(
                    "\"relation\":{",
                    "\"href\":\"address\",",
                    "\"type\":\"image/*\",",
                    "\"hreflang\":\"zh\",",
                    "\"templated\":\"true\",",
                    "\"name\":\"test name\",",
                    "\"title\":\"test title\",",
                    "\"profile\":\"test profile\",",
                    "\"deprecation\":\"deprecation+info\"",
                    "}").joinToString("")

        }

        "Invalid parameters" {
            (
                    {
                        Link("", URI(""))
                    } shouldThrow InvalidParametersException::class
                    ).exception.localizedMessage shouldStartWith  "Invalid parameters for Link:"
        }
    }
}
