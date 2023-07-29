package com.mukundi.rabbitmq.visualizer.consumer;

import com.mukundi.rabbitmq.visualizer.dto.Notification;
import com.mukundi.rabbitmq.visualizer.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RabbitConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(Order order) throws Exception {
    Notification notification = new Notification(order.orderId(),
            "Your order with id " + order.orderId() + " has been received", LocalDateTime.now());
    send(notification);
    try { Thread.sleep(7000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Notification: " + notification));
  }

//  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
//  public void consume2(String message) throws Exception {
//    send("Received");
//    try { Thread.sleep(3000);} catch (InterruptedException ignored) {}
//    LOGGER.info(String.format("Received message -> %s", message + " from consumer 2"));
//  }

  @MessageMapping("/chat")
  public void send(@Payload Notification notification) throws Exception {
    simpMessagingTemplate.convertAndSend("/topic/consumer", notification);
  }

}
