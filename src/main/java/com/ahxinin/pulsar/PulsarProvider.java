package com.ahxinin.pulsar;

import com.ahxinin.pulsar.annotation.PulsarConsumer;
import com.ahxinin.pulsar.annotation.PulsarProducer;
import com.ahxinin.pulsar.collector.PulsarConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.PulsarClientImpl.PulsarClientImplBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.util.Map;

/**
 * Pulsar provider
 */
@Slf4j
public class PulsarProvider {

    private String serviceUrl;

    private String token;

    private PulsarClient pulsarClient;

    @Autowired
    private Environment environment;

    public void init() throws PulsarClientException {
        if (pulsarClient == null){
            ClientBuilder clientBuilder = PulsarClient.builder()
                    .serviceUrl(serviceUrl);
            if (StringUtils.hasLength(token)){
                clientBuilder.authentication(AuthenticationFactory.token(token));
            }
            pulsarClient = clientBuilder.build();
        }
    }

    public void destroy() throws PulsarClientException {
        if (pulsarClient != null){
            pulsarClient.close();
        }
    }

    public Producer createProducer(PulsarProducer pulsarProducer) throws PulsarClientException {
        String topic = getConfigValue(pulsarProducer.topic());
        return pulsarClient.newProducer()
                .topic(topic)
                .create();
    }

    public Consumer createConsumer(PulsarConsumerProperties properties) throws PulsarClientException {
        PulsarConsumer pulsarConsumer = properties.getPulsarConsumer();
        MessageListener<byte[]> listener = (Consumer,Message) -> properties.getConsumerMessage().receiver(Consumer, Message);

        String topic = getConfigValue(pulsarConsumer.topic());
        String subscription = getConfigValue(pulsarConsumer.subscription());
        return pulsarClient.newConsumer()
                .topic(topic)
                .subscriptionName(subscription)
                .messageListener(listener)
                .subscribe();
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String getConfigValue(String placeholder){
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${","}");
        return helper.replacePlaceholders(placeholder, key-> environment.getProperty(key));
    }
}
