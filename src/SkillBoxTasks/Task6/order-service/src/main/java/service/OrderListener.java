package service;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderListener {
    @KafkaListener(topics = {"order-status-topic"})
    public void listenStatus(@Payload String message,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                             @Header(KafkaHeaders.KEY) String key,
                             @Header(KafkaHeaders.TOPIC) String topic,
                             @Header(KafkaHeaders.TIMESTAMP) String timestamp){
        log.info("Received message: {}", message);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
