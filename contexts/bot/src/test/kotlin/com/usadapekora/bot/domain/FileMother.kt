package com.usadapekora.bot.domain

object FileMother {
    fun filename(extension: String = Random.instance().file.extension())
        = "${Random.instance().internet.slug()}.${extension.removePrefix(".")}"
}
