package com.store.notification.component.impl;

import com.netflix.discovery.converters.Auto;
import com.store.notification.component.RabbitMQComponent;
import com.store.notification.service.impl.EmailServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class RabbitMQComponentImpl implements RabbitMQComponent {

    // pego essa informação para ter, pois posso usar dentro do handle message. Olhar anotação *1
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Autowired
    private EmailServiceImpl emailService;

    private final WebClient webClient;

    public RabbitMQComponentImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    // eu quero enxergar a order_notification (var ambiente)
    // esse RabbitListener vai pegar o item que vem da fila do rabbitMQ
    // e vai fazer o que quiser quando pegar
    @RabbitListener(queues = "order_notification")
    public void handleMessage(String message) {

        // anotação *1
        // posso usar dessa forma
        /*
        System.out.print("Recebi algo da fila" + queue);
        */
        Map<String, Object> obj = emailService.convertToObject(message);
        int user_id = (int) obj.get("user_id");
        String product_name = (String) obj.get("product_name");

        String response = this.webClient.get()
                .uri("/user/"+ String.valueOf(user_id))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, Object> user = emailService.convertToObject(response);

        String content = emailService.constructContent(product_name, (String) user.get("username"));
        emailService.sendEmail(content, (String) user.get("email"), "Notificação XPTO");


    }
}