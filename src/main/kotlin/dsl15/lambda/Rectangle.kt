package dsl15.lambda

import java.net.URI
import java.util.*

data class Rectangle(
        val x: Distance?,
        val y: Distance?,
        val width: Distance?,
        val height: Distance?
//        val style: Style
) {
    //    init {
//        require (rel.isNotEmpty()) { "Invalid parameters for Link: 'rel' cannot be empty" }
//        require (href.toString().isNotEmpty()) { "URL string must not be empty" }
//    }
    private fun toString(name: String, value: Any?): String {
        return when (value) {
            is Unit -> ""
            is String -> "$name=\"$value\""
            is Number -> "$name=$value"
            is Distance -> "$name=$value"
            else -> "$name=\"${value.toString()}\""
        }
    }

    override fun toString(): String {
        return mapOf<String, Any?>(
                "x" to x,
                "y" to y,
                "width" to width,
                "height" to height
        ).filter { (key, value) ->
            value != null
        }.map { (key, value) ->
                    if (value != null) toString(key, value) else null
                }.joinToString(", ", "<rect ", "/>")
    }
}

infix fun Point.rect(init: RectBuilder.()->Unit): Rectangle {
    val builder = RectBuilder(this?.x, this?.y)
    builder.init()
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(init: RectBuilder.()->Unit): Rectangle {
    val builder = RectBuilder(null, null)
    builder.init()
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}

infix fun Point.rect(size: Point): Rectangle {
    val builder = RectBuilder(this.x, this.y)
    if (size != null) {
        builder.width = size.x
        builder.height = size.y
    }
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(size: Point): Rectangle {
    val builder = RectBuilder(null, null)
    if (size != null) {
        builder.width = size.x
        builder.height = size.y
    }
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}

fun Point.rect(width: Distance? = null, height: Distance? = null): Rectangle {
    val builder = RectBuilder(this.x, this.y)
        builder.width = width
        builder.height = height
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(width: Distance? = null, height: Distance? = null): Rectangle {
    val builder = RectBuilder(null, null)
    builder.width = width
    builder.height = height
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}

fun Point.rect(width: Number, height: Distance? = null): Rectangle {
    val builder = RectBuilder(this.x, this.y)
    builder.width = Distance(width)
    builder.height = height
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(width: Number, height: Distance? = null): Rectangle {
    val builder = RectBuilder(null, null)
    builder.width = Distance(width)
    builder.height = height
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}

fun Point.rect(width: Distance, height: Number): Rectangle {
    val builder = RectBuilder(this.x, this.y)
    builder.width = width
    builder.height = Distance(height)
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(width: Distance, height: Number): Rectangle {
    val builder = RectBuilder(null, null)
    builder.width = width
    builder.height = Distance(height)
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}

fun Point.rect(width: Number, height: Number): Rectangle {
    val builder = RectBuilder(this.x, this.y)
    builder.width = Distance(width)
    builder.height = Distance(height)
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}
fun rect(width: Number, height: Number): Rectangle {
    val builder = RectBuilder(null, null)
    builder.width = Distance(width)
    builder.height = Distance(height)
    return Rectangle(builder.x, builder.y, builder.width, builder.height)
}


class RectBuilder(val x: Distance?, val y: Distance?) {
    var width: Distance? = null
    var height: Distance? = null

    val WIDTH: RectBuilder.(Distance)->Unit = { width -> this.width = width }

    val HEIGHT: RectBuilder.(Distance)->Unit = { height -> this.height = height }

    infix fun Number.pt(setter: RectBuilder.(Distance)->Unit) {
        this@RectBuilder.setter(Distance(this, DIST_UNIT.PT))
    }
    fun Number.pc(setter: RectBuilder.(Distance)->Unit) {
        this@RectBuilder.setter(Distance(this, DIST_UNIT.PC))
    }
}