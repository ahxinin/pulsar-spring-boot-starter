package com.ahxinin.pulsar.consumer;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.util.RetryMessageUtil;
import org.apache.pulsar.shade.com.google.gson.Gson;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;

/**
 * Pulsar consumer message
 */
@Slf4j
public abstract class PulsarConsumerMessage<T> implements ConsumerMessage{

    Class _class = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private Consumer consumer;

    private static final long MAX_RETRY_TIMES  = 10;

    private static final long DELAY_TIME_SECOND = 60;

    @Override
    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void receiver(Consumer consumer, Message message) {
        try {
            String content = new String(message.getData(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Object object = gson.fromJson(content, _class);
            handel((T) object);

            //消息ACK
            ackMessage(consumer, message);
        }catch (Exception e){
            log.error("pulsar receiver error", e);

            //进行有限条件下的重试
            String retryTimesStr = message.getProperty(RetryMessageUtil.SYSTEM_PROPERTY_RECONSUMETIMES);
            long retryTimes = StringUtils.isEmpty(retryTimesStr) ? 0 : Long.parseLong(retryTimesStr);
            if (retryTimes > MAX_RETRY_TIMES){
                ackMessage(consumer, message);
            }else {
                retryMessage(consumer, message);
            }
        }
    }

    public abstract void handel(T message);

    private void ackMessage(Consumer consumer, Message message){
        try {
            consumer.acknowledge(message);
        } catch (PulsarClientException e) {
            consumer.negativeAcknowledge(message);
        }
    }

    private void retryMessage(Consumer consumer, Message message){
        try {
            consumer.reconsumeLater(message, DELAY_TIME_SECOND, TimeUnit.SECONDS);
        } catch (PulsarClientException e) {
            consumer.negativeAcknowledge(message);
        }
    }
}
