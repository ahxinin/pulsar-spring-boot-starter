package com.ahxinin.pulsar.producer;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.shade.com.google.gson.Gson;

/**
 * Pulsar producer message
 */
@Slf4j
public class PulsarProducerMessage<T> implements ProducerMessage{

    private Producer producer;

    @Override
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public void send(T message) throws PulsarClientException {
        Gson gson = new Gson();
        String content = gson.toJson(message);
        MessageId messageId = producer.send(content.getBytes(StandardCharsets.UTF_8));
        log.info("send success, messageId:{}", messageId);
    }
}
