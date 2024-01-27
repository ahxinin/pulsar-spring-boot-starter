package com.ahxinin.pulsar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Pulsar auto configuration
 */
@Configuration
public class PulsarAutoConfiguration {

    @Value("${pulsar.serviceUrl}")
    private String serviceUrl;

    @Value("${pulsar.token}")
    private String token;

    @ConditionalOnMissingBean
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PulsarProvider pulsarProvider(){
        PulsarProvider pulsarProvider = new PulsarProvider();
        pulsarProvider.setServiceUrl(serviceUrl);
        pulsarProvider.setToken(token);
        return pulsarProvider;
    }
}
