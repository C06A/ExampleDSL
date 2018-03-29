package dsl01.Naive

import com.winterbe.expekt.should
import org.junit.Test
import java.net.URI
import java.util.*


class LinkExpekt {
    val linkList = listOf<Link>(
            Link("relation", URI("address")),
            Link("relation",
                    URI("address"),
                    "image/*",
                    Locale.CHINESE,
                    "test name",
                    "test title",
                    "test profile",
                    URI("deprecation+info"),
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

    @Test
    fun minLink() {
        linkMin.rel.should.be.equal("relation")
        linkMin.href.should.be.equal(URI("address"))
        linkMin.type.should.equal("application/hal+json")
        linkMin.hreflang.should.be.equal(null)
        linkMin.name.should.be.equal(null)
        linkMin.title.should.be.equal(null)
        linkMin.profile.should.be.equal(null)
        linkMin.deprecation.should.be.equal(null)
        linkMin.templated.should.be.equal(null)
    }

    @Test
    fun maxLink() {
        linkMax.rel.should.be.equal("relation")
        linkMax.href.should.be.equal(URI("address"))
        linkMax.type.should.equal("image/*")
        linkMax.hreflang.should.be.equal(Locale.CHINESE)
        linkMax.name.should.be.equal("test name")
        linkMax.title.should.be.equal("test title")
        linkMax.profile.should.be.equal("test profile")
        linkMax.deprecation.should.be.equal(URI("deprecation+info"))
        linkMax.templated.should.be.equal(true)
    }

    @Test
    fun namedLink() {
        linkByNames.rel.should.be.equal("relation")
        linkByNames.href.should.be.equal(URI("address"))
        linkByNames.type.should.equal("image/*")
        linkByNames.hreflang.should.be.equal(Locale.CHINESE)
        linkByNames.name.should.be.equal("test name")
        linkByNames.title.should.be.equal("test title")
        linkByNames.profile.should.be.equal("test profile")
        linkByNames.deprecation.should.be.equal(URI("deprecation+info"))
        linkByNames.templated.should.be.equal(true)
    }
}