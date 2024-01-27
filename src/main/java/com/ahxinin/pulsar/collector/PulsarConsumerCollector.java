package com.ahxinin.pulsar.collector;

import com.ahxinin.pulsar.annotation.PulsarConsumer;
import com.ahxinin.pulsar.consumer.ConsumerMessage;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * Collection registered consumers
 */
@Slf4j
@Configuration
public class PulsarConsumerCollector implements BeanPostProcessor {

    private final ConcurrentHashMap<String,PulsarConsumerProperties> consumers = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Class<?> beanClass = bean.getClass();
        PulsarConsumer pulsarConsumer = beanClass.getAnnotation(PulsarConsumer.class);

        if (pulsarConsumer!=null && bean instanceof ConsumerMessage){
            PulsarConsumerProperties properties = PulsarConsumerProperties.builder()
                    .consumerMessage((ConsumerMessage) bean)
                    .pulsarConsumer(pulsarConsumer)
                    .build();
            consumers.put(beanName, properties);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    public ConcurrentHashMap<String, PulsarConsumerProperties> getConsumers() {
        return consumers;
    }
}
