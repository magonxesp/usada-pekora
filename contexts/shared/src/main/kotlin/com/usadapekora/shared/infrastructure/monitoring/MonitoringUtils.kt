package com.usadapekora.shared.infrastructure.monitoring

fun createMetricName(context: String, moduleOrApp: String, metric: String) =
    "usada_pekora_${context}_${moduleOrApp}_$metric"
