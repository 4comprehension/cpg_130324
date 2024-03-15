package com.pivovarit.domain.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitMQConfiguration {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory("localhost");
        cf.setConnectionNameStrategy(f -> "rental-service");
        return cf;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper om) {
        return new Jackson2JsonMessageConverter(om);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MovieDescriptionsUpdater descriptionsUpdater() {
        return new MovieDescriptionsUpdater();
    }

    @Bean
    public DescriptionsMessageListener messageListener(MovieDescriptionsUpdater updater, ObjectMapper om) {
        return new DescriptionsMessageListener(updater, om);
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Queue rentalServiceSubscriberQueue, DescriptionsMessageListener descriptionsMessageListener) {
        var container = new DirectMessageListenerContainer(connectionFactory);
        container.setQueues(rentalServiceSubscriberQueue);
        container.setMessageListener(descriptionsMessageListener);
        return container;
    }

    @Bean
    Queue rentalHistoryEventsPeeker() {
        return new Queue("peeker", false);
    }

    @Bean
    Binding rentalHistoryBinding(Queue rentalHistoryEventsPeeker, TopicExchange rentalHistoryTopic) {
        return BindingBuilder.bind(rentalHistoryEventsPeeker).to(rentalHistoryTopic).with(MovieRentalEvent.class.getName());
    }

    @Bean
    TopicExchange rentalHistoryTopic() {
        return new TopicExchange("rental-history-topic");
    }

    @Bean
    Queue rentalServiceSubscriberQueue() {
        return new Queue("rental-service-subscriber", false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("movie-descriptions-topic");
    }

    @Bean
    Binding binding(Queue rentalServiceSubscriberQueue, TopicExchange exchange) {
        return BindingBuilder.bind(rentalServiceSubscriberQueue).to(exchange).with("com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent");
    }
}
