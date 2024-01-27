package com.ahxinin.pulsar.consumer;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;

/**
 * Pulsar consumer message
 */
public interface ConsumerMessage {

    void setConsumer(Consumer consumer);

    void receiver(Consumer consumer, Message message);
}
