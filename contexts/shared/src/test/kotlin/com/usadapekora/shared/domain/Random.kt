package com.usadapekora.shared.domain

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker

object Random {
    fun instance(): Faker = faker { }
}
