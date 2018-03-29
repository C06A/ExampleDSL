package dsl15.lambda

import java.awt.Label

enum class DIST_UNIT(private val lbl: String? = null) {
    EM, EX, PX, PT, PC, CM, MM, IN
    , PerCent("%");

    val label: String
    get() {
        return lbl ?: name.toLowerCase()
    }
}

class Distance(val distance: Number, val unit: DIST_UNIT? = null) {
    override fun toString(): String {
        return if (unit == null) {
            "$distance"
        } else {
            "\"$distance${unit.label}\""
        }
    }
}

val Number.em: Distance
    get() = Distance(this, DIST_UNIT.EM)
val Number.ex: Distance
    get() = Distance(this, DIST_UNIT.EX)
val Number.px: Distance
    get() = Distance(this, DIST_UNIT.PX)
val Number.pt: Distance
    get() = Distance(this, DIST_UNIT.PT)
val Number.pc: Distance
    get() = Distance(this, DIST_UNIT.PC)
val Number.cm: Distance
    get() = Distance(this, DIST_UNIT.CM)
val Number.mm: Distance
    get() = Distance(this, DIST_UNIT.MM)
val Number.inch: Distance
    get() = Distance(this, DIST_UNIT.IN)
val Int.percent: Distance
    get() = Distance(this, DIST_UNIT.PerCent)

data class Point(val x: Distance , val y: Distance)

infix fun Number.X(y: Number): Point {
    return Point(Distance(this), Distance(y))
}
infix fun Number.X(y: Distance): Point {
    return Point(Distance(this), y)
}
infix fun Distance.X(y: Number): Point {
    return Point(this, Distance(y))
}
infix fun Distance.X(y: Distance): Point {
    return Point(this, y)
}