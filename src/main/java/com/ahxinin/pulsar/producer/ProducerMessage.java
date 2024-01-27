package com.ahxinin.pulsar.producer;

import org.apache.pulsar.client.api.Producer;

/**
 * Pulsar producer message
 */
public interface ProducerMessage {

    void setProducer(Producer producer);
}
