package com.ahxinin.pulsar.collector;

import com.ahxinin.pulsar.annotation.PulsarConsumer;
import com.ahxinin.pulsar.consumer.ConsumerMessage;
import lombok.Builder;
import lombok.Data;

/**
 * Pulsar consumer properties
 */
@Data
@Builder
public class PulsarConsumerProperties {

    private PulsarConsumer pulsarConsumer;

    private ConsumerMessage consumerMessage;
}
