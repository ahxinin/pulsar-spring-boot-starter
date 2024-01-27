package com.ahxinin.pulsar;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 生产者测试类
 * @date : 2024-01-26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private TranslateProducer translateItemProducer;

    @Test
    public void test() throws PulsarClientException {
        TranslateMessage message = new TranslateMessage();
        message.setTaskId("123");
        translateItemProducer.send(message);
    }
}
