package com.ahxinin.pulsar;

import com.ahxinin.pulsar.annotation.PulsarProducer;
import com.ahxinin.pulsar.producer.PulsarProducerMessage;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

/**
 * @description: 翻译任务生产者
 */
@Component
@PulsarProducer(topic = "${pulsar.topic.task}")
public class TranslateProducer extends PulsarProducerMessage<TranslateMessage> {

    @Override
    public void send(TranslateMessage message) throws PulsarClientException {
        super.send(message);
    }
}
