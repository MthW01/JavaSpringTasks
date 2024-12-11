package service;

import dto.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderDTO request) {
        kafkaTemplate.send("order-topic",request);
    }
}
