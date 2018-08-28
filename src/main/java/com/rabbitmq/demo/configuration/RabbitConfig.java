package com.rabbitmq.demo.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

    @Bean
    public Queue queue2() {
        return new Queue("msg");
    }

    @Bean(name = "Amessage")
    public Queue Amessage() {
        return new Queue("fanout.A");
    }

    @Bean(name = "Bmessage")
    public Queue Bmessage() {
        return new Queue("fanout.B");
    }

    @Bean(name = "Cmessage")
    public Queue Cmessage() {
        return new Queue("fanout.C");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchangeA(@Qualifier("Amessage") Queue Amessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(Amessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeB(@Qualifier("Bmessage") Queue Bmessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(Bmessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeC(@Qualifier("Cmessage") Queue Cmessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(Cmessage).to(fanoutExchange);
    }

    @Bean(name = "TopicA")
    public Queue topicQueue1() {
        return new Queue("topic.message");
    }

    @Bean(name = "TopicB")
    public Queue topicQueue2() {
        return new Queue("topic.messages");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("TopicExchange");
    }

    @Bean
    Binding bindingTopic1(@Qualifier("TopicA") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.message");
    }

    @Bean
    Binding bingdingTopic2(@Qualifier("TopicB") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("topic.#");
    }
}
