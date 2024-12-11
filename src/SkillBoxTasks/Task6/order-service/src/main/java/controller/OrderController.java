package controller;

import lombok.RequiredArgsConstructor;
import dto.OrderDTO;
import service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/send")
    public void sendOrderEvent(@RequestBody OrderDTO request){
        orderService.send(request);
    }
}
