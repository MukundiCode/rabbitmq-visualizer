package com.mukundi.rabbitmq.visualizer.consumer;

import com.mukundi.rabbitmq.visualizer.dto.Notification;
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
  public void consume(Notification notification) throws Exception {
    send(notification);
    try { Thread.sleep(7000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Notification: " + notification));
  }

  @MessageMapping("/chat")
  public void send(@Payload Notification notification) throws Exception {
    simpMessagingTemplate.convertAndSend("/topic/consumer", notification);
  }

}
