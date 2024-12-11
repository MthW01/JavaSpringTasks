package orderService;

import lombok.extern.log4j.Log4j2;
import dto.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class OrderService {
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-topic")
    public void listenOrder(OrderDTO request) {
        log.info("Received order: {}", request);
        sendInfo(request);
    }

    public void sendInfo(OrderDTO request) {
        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        var status = List.of("CREATED", "PROCESS").get(new Random().nextInt(2));

        stringBuilder.append(status);
        stringBuilder.append(" time: ");
        stringBuilder.append(format.format(LocalDate.now()));

        OrderDTO statusUpdate = new OrderDTO();
        kafkaTemplate.send("order-status-topic", statusUpdate);

        log.info("Sent order status: {}", statusUpdate);
    }
}
