package com.store.notification.configutarion;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfigEmail {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // Configuração da fila
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder // estou dizendo que a minha fila com as informações das variáveis de ambiente serão passadas aqui
                .bind(queue()) // construção da queue
                .to(exchange()) // construção da queue apontando para o exchange
                .with(routingKey); // passo a rota
    }
}
