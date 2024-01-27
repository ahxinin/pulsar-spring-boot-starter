package com.ahxinin.pulsar.collector;

import com.ahxinin.pulsar.annotation.PulsarProducer;
import com.ahxinin.pulsar.producer.ProducerMessage;
import lombok.Builder;
import lombok.Data;

/**
 * Pulsar producer properties
 */
@Data
@Builder
public class PulsarProducerProperties {

    private PulsarProducer pulsarProducer;

    private ProducerMessage producerMessage;
}
