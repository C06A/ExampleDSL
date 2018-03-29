package dsl11.getter

import io.kotlintest.matchers.*
import io.kotlintest.specs.StringSpec
import java.net.URI
import java.util.*

class LinkSpec: StringSpec() {
    init {
        val linkList = listOf<Link>(
                "relation" link "address",
                "relation" link {
                    HREF["address"]
                    TYPE["image/*"]
                    HREFLANG[Locale.CHINESE.toLanguageTag()]
                    NAME["test name"]
                    TITLE["test title"]
                    PROFILE["test profile"]
                    DEPRECATION["deprecation+info"]
                    TEMPLATED["true"]
                },
                "relation" link {
                    PROFILE["test profile"]
                    HREF["address"]
                    TEMPLATED["true"]
                    DEPRECATION["deprecation+info"]
                    TITLE["test title"]
                    NAME["test name"]
                    HREFLANG["zh"]
                    TYPE["image/*"]
                }
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
            link.deprecation.toString() shouldBe "deprecation+info"
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
            val e = shouldThrow<IllegalArgumentException> {
                "" link URI("")
            }

            val msg = e.message
            when (msg) {
                null -> AssertionError("Message in exception cannot be null")
                is String -> msg should include("'rel'")
            }
        }

        "Empty link" {
            val e = shouldThrow<IllegalArgumentException> {
                "empty" link { }
            }

            val msg = e.message
            println(msg)
            when (msg) {
                null -> AssertionError("Message in exception cannot be null")
                is String -> msg should include("empty")
            }
        }
    }
}
