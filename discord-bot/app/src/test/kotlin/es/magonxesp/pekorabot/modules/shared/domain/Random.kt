package es.magonxesp.pekorabot.modules.shared.domain

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker

class Random {
    companion object {
        fun instance(): Faker = faker { }
    }
}
