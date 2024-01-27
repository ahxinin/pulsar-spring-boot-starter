package com.ahxinin.pulsar.create;

import com.ahxinin.pulsar.PulsarProvider;
import com.ahxinin.pulsar.collector.PulsarProducerCollector;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Create producers
 */
@Slf4j
@Component
@DependsOn({"pulsarAutoConfiguration","pulsarProducerCollector"})
public class PulsarProducerCreate {

    @Resource
    private PulsarProducerCollector collector;

    @Resource
    private PulsarProvider pulsarProvider;

    @PostConstruct
    public void create(){
        collector.getProducers().values().forEach(properties->{
            try {
                Producer producer = pulsarProvider.createProducer(properties.getPulsarProducer());
                properties.getProducerMessage().setProducer(producer);
            } catch (PulsarClientException e) {
                log.error("create producer error, pulsarProducer:{}",properties.getPulsarProducer(),e);
            }
        });
    }
}
