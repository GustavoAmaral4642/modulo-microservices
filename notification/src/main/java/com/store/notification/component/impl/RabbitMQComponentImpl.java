package com.store.notification.component.impl;

import com.store.notification.component.RabbitMQComponent;
import com.store.notification.service.impl.EmailServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQComponentImpl implements RabbitMQComponent {

    // pego essa informação para ter, pois posso usar dentro do handle message. Olhar anotação *1
    @Value("${rabbitmq.queue.name}")
    private String queue;

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

        String obj = EmailServiceImpl.convertToObject(message);
        String content = EmailServiceImpl.constructContent();
        EmailServiceImpl.sendEmail("Conteúdo", obj);
        // TODO receber o dado
        // TODO identificar produto e usuário
        // TOTO enviar um e-mail para um usuário
    }
}