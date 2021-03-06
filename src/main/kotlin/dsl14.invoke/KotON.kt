package dsl14.invoke

/**
 * This was inspired by project https://github.com/Jire/KTON.
 *
 * Here I spread content into class hierarchy which helps to scope functions
 * and doesn't require both Map and Array for each instance.
 * As a result access to the value requires extra parenthesis.
 *
 * Also I added toJson() function to convert the Object into (you guessed it) JSON.
 */
open class KotON() {
    open fun toJson(): String {
        return "{}"
    }

    open operator fun get(key: String): KotON {
        throw Exception("Key access is not supported by this instance")
    }

    open operator fun get(index: Int): KotON {
        throw Exception("Index is not supported by this instance")
    }

    open operator fun invoke(): Any? {
        return null
    }
}

data class KotONVal<out T>(val value: T): KotON() {
    override fun toJson(): String {
        return value.toString()
    }

    override operator fun invoke(): Any? {
        return value
    }
}

data class KotONString(val value: String): KotON() {
    override fun toJson(): String {
        return "\"$value\""
    }

    override operator fun invoke(): Any? {
        return value
    }
}

data class KotONArray(val value: ArrayList<KotON> = ArrayList()): KotON() {
    override fun toJson(): String {
        return value.map {
            it.toJson()
        }.joinToString(prefix = "[", postfix = "]")
    }

    operator fun plus(body: KotONBuilder.() -> Unit): KotON {
        value += kotON(body)
        return this
    }

    override operator fun get(index: Int): KotON {
        return value[index]
    }
}

data class KotONEntry(val content: Map<String, KotON> = emptyMap()): KotON() {
    override fun toJson(): String {
        return content.entries.joinToString(prefix = "{", postfix = "}") {
            "\"${it.key}\": ${it.value.toJson()}"
        }
    }

    override operator fun get(key: String): KotON {
        return content[key] ?: super.get(key)
    }
}

data class KotONBuilder(val content: MutableMap<String, KotON> = mutableMapOf()) {
    infix fun String.to(value: Int) {
        content[this] = KotONVal(value)
    }

    infix fun String.to(value: Long) {
        content[this] = KotONVal(value)
    }

    infix fun String.to(value: Float) {
        content[this] = KotONVal(value)
    }

    infix fun String.to(value: Double) {
        content[this] = KotONVal(value)
    }

    infix fun String.to(value: Boolean) {
        content[this] = KotONVal(value)
    }

    infix fun String.to(value: String) {
        content[this] = KotONString(value)
    }

    operator fun String.invoke(body: KotONBuilder.() -> Unit) {
        content[this] = kotON(body)
    }

    operator fun String.get(vararg bodies: KotONBuilder.() -> Unit) {
        val kotON = KotONArray()
        bodies.forEach {
            kotON + it
        }
        content[this] = kotON
    }

    fun build(): KotON {
        return KotONEntry(content)
    }
}

inline fun kotON(init: KotONBuilder.() -> Any): KotON {
    val root = KotONBuilder()
    val value = root.init()
    return when(value) {
        is Boolean, is Int, is Long, is Float, is Double -> KotONVal(value)
        is String -> KotONString(value)
        is KotON -> value
        is Unit -> root.build()
        else -> throw Exception("Unsupported value")
    }
}
