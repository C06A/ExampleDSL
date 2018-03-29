package dsl15.lambda

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class RectangleSpec: StringSpec() {
    init {
        "default rect" {
            val rect = 50 X 60 rect { }
            rect.toString() shouldBe "<rect x=50, y=60/>"
        }

        "mix of Coordinates types" {
            (
                    10 X 20 rect {}
                    ).toString() shouldBe "<rect x=10, y=20/>"

            (
                    123456789012345 X 20 rect {}
                    ).toString() shouldBe "<rect x=123456789012345, y=20/>"

            (
                    10 X 123456789012345 rect {}
                    ).toString() shouldBe "<rect x=10, y=123456789012345/>"

            (
                    10.5 X 20 rect {}
                    ).toString() shouldBe "<rect x=10.5, y=20/>"

            (
                    10 X 20.5 rect {}
                    ).toString() shouldBe "<rect x=10, y=20.5/>"

            (
                    10.5 X 20.5 rect {}
                    ).toString() shouldBe "<rect x=10.5, y=20.5/>"
        }

        "different units" {
            (
                    10.1.em X 20.2.ex rect {}
                    ).toString() shouldBe "<rect x=\"10.1em\", y=\"20.2ex\"/>"
            (
                    10L.pt X 20.px rect {}
                    ).toString() shouldBe "<rect x=\"10pt\", y=\"20px\"/>"
            (
                    10.pc X 20.inch rect {}
                    ).toString() shouldBe "<rect x=\"10pc\", y=\"20in\"/>"
            (
                    10.cm X 20.mm rect {}
                    ).toString() shouldBe "<rect x=\"10cm\", y=\"20mm\"/>"
            (
                    10 X 20.percent rect {}
                    ).toString() shouldBe "<rect x=10, y=\"20%\"/>"
        }

        "not-anchored function" {
            rect {
                15 pt WIDTH
                25.3.pc(HEIGHT)
            }.toString() shouldBe "<rect width=\"15pt\", height=\"25.3pc\"/>"
        }
    }
}