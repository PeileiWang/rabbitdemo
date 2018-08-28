package com.rabbitmq.demo.sender;

import com.rabbitmq.demo.domain.User;
import com.rabbitmq.demo.utils.JsonUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rabbitmq.demo.utils.JsonUtils.objectToJson;


@RestController
@RequestMapping("/mq")
public class HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("/sendusr")
    public User send() {
        User user = new User();
        user.setId(1);
        user.setName("Wang");
        String msg = JsonUtils.objectToJson(user);
        rabbitTemplate.convertAndSend("hello", msg);
        return user;
    }

    @RequestMapping("/sendmsg")
    public String sendMsg() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long time = System.nanoTime();
            rabbitTemplate.convertAndSend("msg", "第 " + i + "次发送的时间：" + time);
            stringBuilder.append(time + "<br>");
        }
        return stringBuilder.toString();
    }

    @RequestMapping("/fanout")
    public void sendFanout() {
        rabbitTemplate.convertAndSend("fanoutExchange", "", "Fanout Message Test!");
    }

    @RequestMapping("/topic")
    public void sendTopic() {
        rabbitTemplate.convertAndSend("TopicExchange", "topic.message", "Topic Test");
    }
}


