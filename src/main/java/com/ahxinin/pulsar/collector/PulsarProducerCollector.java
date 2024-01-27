package com.ahxinin.pulsar.collector;

import com.ahxinin.pulsar.annotation.PulsarProducer;
import com.ahxinin.pulsar.producer.ProducerMessage;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * Collection registered producers
 */
@Configuration
public class PulsarProducerCollector implements BeanPostProcessor {

    private final ConcurrentHashMap<String, PulsarProducerProperties> producers = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Class<?> beanClass = bean.getClass();
        PulsarProducer pulsarProducer = beanClass.getAnnotation(PulsarProducer.class);

        if (pulsarProducer!=null && bean instanceof ProducerMessage){
            ProducerMessage producerMessage = (ProducerMessage) bean;

            PulsarProducerProperties properties = PulsarProducerProperties.builder()
                    .producerMessage(producerMessage)
                    .pulsarProducer(pulsarProducer)
                    .build();
            producers.put(beanName, properties);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    public ConcurrentHashMap<String, PulsarProducerProperties> getProducers() {
        return producers;
    }
}
