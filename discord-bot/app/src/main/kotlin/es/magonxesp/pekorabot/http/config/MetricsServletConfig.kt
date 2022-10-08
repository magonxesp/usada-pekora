package es.magonxesp.pekorabot.http.config

import io.prometheus.client.exporter.MetricsServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsServletConfig {

    @Bean
    fun registerMetricsServlet() = ServletRegistrationBean(MetricsServlet(), "/metrics")

}
