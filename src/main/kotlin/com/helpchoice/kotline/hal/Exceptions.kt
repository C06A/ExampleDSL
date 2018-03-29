package com.helpchoice.kotline.hal

abstract class HalException(message: String, cause: Throwable?, vararg val memberName: Any) : Exception(message, cause) {
    constructor(message: String, vararg memberName: Any): this(message, null, memberName)

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage().format(memberName)
    }
}

class MissingMemberException(message: String, cause: Throwable?, vararg memberName: Any): HalException(message, cause, memberName) {
    constructor(message: String, vararg memberName: Any): this(message, null, memberName)
}

class InvalidParametersException(message: String, cause: Throwable?, vararg memberName: Any): HalException(message, cause, memberName) {
    constructor(message: String, vararg memberName: Any): this(message, null, memberName)
}