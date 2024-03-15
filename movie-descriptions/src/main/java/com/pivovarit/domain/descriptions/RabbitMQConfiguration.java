package com.pivovarit.domain.descriptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class RabbitMQConfiguration {

    @Bean
    @Profile("prod")
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory("localhost");
        cf.setConnectionNameStrategy(f -> "movie-descriptions");
        return cf;
    }

    @Bean
    @Profile("prod")
    public MessageConverter messageConverter(ObjectMapper om) {
        return new Jackson2JsonMessageConverter(om);
    }

    @Bean
    @Profile("prod")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    @Profile("prod")
    Queue queue() {
        return new Queue("movie-descriptions", false);
    }

    @Bean
    @Profile("prod")
    TopicExchange exchange() {
        return new TopicExchange("movie-descriptions-topic");
    }

    @Profile("prod")
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DescriptionUpdatedEvent.class.getName());
    }
}
