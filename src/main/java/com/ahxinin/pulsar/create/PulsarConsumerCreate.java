package com.ahxinin.pulsar.create;

import com.ahxinin.pulsar.PulsarProvider;
import com.ahxinin.pulsar.collector.PulsarConsumerCollector;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Create consumers
 */
@Slf4j
@Component
@DependsOn({"pulsarAutoConfiguration","pulsarConsumerCollector"})
public class PulsarConsumerCreate {

    @Resource
    private PulsarConsumerCollector collector;

    @Resource
    private PulsarProvider provider;

    @PostConstruct
    public void create(){
        collector.getConsumers().values().forEach(properties->{
            try {
                Consumer consumer = provider.createConsumer(properties);
                properties.getConsumerMessage().setConsumer(consumer);
            }catch (Exception e){
                log.error("create consumer error, pulsarConsumer:{}",properties.getPulsarConsumer(),e);
            }
        });
    }
}
