package com.mukundi.rabbitmq.visualizer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(String message) throws Exception {
    send("Received");
    try { Thread.sleep(7000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Received message -> %s", message + " from consumer 1"));
  }

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume2(String message) throws Exception {
    send("Received");
    try { Thread.sleep(3000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Received message -> %s", message + " from consumer 2"));
  }

  @MessageMapping("/chat")
  public void send(@Payload String message) throws Exception {
    System.out.println("Message sent to client");
    simpMessagingTemplate.convertAndSend("/topic/messages", message);
  }

}
