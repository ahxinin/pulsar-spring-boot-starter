## 概览
Pulsar SpringBoot版本实现，支持通过注解方式创建Producer和Consumer，支持自定义消息处理方式（已实现消息失败重试）

## 快速开始

### 1.安装JDK17
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

### 2.安装Pulsar
https://pulsar.apache.org/docs/3.1.x/getting-started-standalone/

### 3.增加Maven依赖
```
    <dependency>
        <groupId>com.ahxinin</groupId>
        <artifactId>pulsar-spring-boot-start</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

### 4.定义Message Object
``` java
@Data
public class TranslateMessage implements Serializable {
    private String taskId;
}
```

### 5.定义Producer
``` java
@Component
@PulsarProducer(topic = "${pulsar.topic.task}")
public class TranslateProducer extends PulsarProducerMessage<TranslateMessage> {

    @Override
    public void send(TranslateMessage message) throws PulsarClientException {
        super.send(message);
    }
}
```

### 6.定义Consumer
``` java
@Slf4j
@Component
@PulsarConsumer(topic = "${pulsar.topic.task}", subscription = "${pulsar.subscription.task}")
public class TranslateConsumer extends PulsarConsumerMessage<TranslateMessage> {

    @Override
    public void handel(TranslateMessage message) {
        log.info("receive message:{}", message.getTaskId());
    }
}
```