package dsl14.invoke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class KotONSpec: StringSpec() {
    init {
        "empty" {
            KotON().toJson() shouldBe "{}"
            kotON { }.toJson() shouldBe "{}"
        }

        "numbers" {
            kotON { 42 }.toJson() shouldBe "42"
            kotON { 3.14 }.toJson() shouldBe "3.14"
        }

        "boolean" {
            kotON { true }.toJson() shouldBe "true"
            kotON { false }.toJson() shouldBe "false"
        }

        "simple collection" {
            kotON {
                "string" to "string value"
                "integer" to 42
                "float" to 3.14
                "boolean true" to true
                "boolean false" to false
            }.toJson() shouldBe
                    "{\"string\": \"string value\", \"integer\": 42, \"float\": 3.14, " +
                    "\"boolean true\": true, \"boolean false\": false}"
        }

        "array of simple" {
            kotON {
                "array"[
                        { "stringElement" to "value of an element" },
                        { "intKey" to 42; "floatKey" to 3.14 },
                        {
                            "boolTrue" to true
                            "booleFalse" to false
                        }
                        ]
            }.toJson() shouldBe
                    "{\"array\": [{\"stringElement\": \"value of an element\"}, " +
                    "{\"intKey\": 42, \"floatKey\": 3.14}, " +
                    "{\"boolTrue\": true, \"booleFalse\": false}]}"
        }

        "complex structure" {
            val doc = kotON {
                "string" to "string value"
                "integer" to 42
                "array"[
                        { "stringElement" to "value of an element" },
                        { "intKey" to 42; "floatKey" to 3.14 },
                        {
                            "boolTrue" to true
                            "booleFalse" to false
                        }
                        ]
                "float" to 3.14
                "boolean true" to true
                "subStruct" {
                    "substring" to "string value"
                    "subinteger" to 42
                    "subfloat" to 3.14
                    "subarray"[
                            { "stringElement" to "value of an element" },
                            { "intKey" to 42; "floatKey" to 3.14 },
                            {
                                "boolTrue" to true
                                "booleFalse" to false
                            }
                            ]
                    "subboolean true" to true
                    "subboolean false" to false
                }
                "boolean false" to false
            }

            doc["string"]() shouldBe "string value"
            doc["integer"]() shouldBe 42
            doc["float"]() shouldBe 3.14
            doc["boolean true"]() shouldBe true
            doc["boolean false"]() shouldBe false

            doc["array"][0]["stringElement"]() shouldBe "value of an element"
        }
    }
}
