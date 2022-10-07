package es.magonxesp.pekorabot.modules.shared.infraestructure.metric

import es.magonxesp.pekorabot.modules.shared.domain.metric.MetricRegister
import io.prometheus.client.Counter

class PrometheusMetricRegister : MetricRegister {

    companion object {

    }

    override fun count(name: String) {
        val counter = Counter.build().name(name).register()
    }

    override fun increase(name: String) {
        TODO("Not yet implemented")
    }

    override fun decrease(name: String) {
        TODO("Not yet implemented")
    }
}
