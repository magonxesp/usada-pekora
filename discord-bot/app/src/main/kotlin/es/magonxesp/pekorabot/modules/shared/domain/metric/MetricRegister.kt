package es.magonxesp.pekorabot.modules.shared.domain.metric

interface MetricRegister {
    fun count(name: String)
    fun increase(name: String)
    fun decrease(name: String)
}
