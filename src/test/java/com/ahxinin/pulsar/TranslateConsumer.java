package com.ahxinin.pulsar;

import com.ahxinin.pulsar.annotation.PulsarConsumer;
import com.ahxinin.pulsar.consumer.PulsarConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 翻译任务消费者
 */
@Slf4j
@Component
@PulsarConsumer(topic = "${pulsar.topic.task}", subscription = "${pulsar.subscription.task}")
public class TranslateConsumer extends PulsarConsumerMessage<TranslateMessage> {

    @Override
    public void handel(TranslateMessage message) {
        log.info("收到任务消息:{}", message.getTaskId());
    }
}
