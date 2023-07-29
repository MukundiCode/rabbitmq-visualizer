package com.mukundi.rabbitmq.visualizer.publisher;

import com.mukundi.rabbitmq.visualizer.dto.Notification;
import com.mukundi.rabbitmq.visualizer.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RabbitProducer {

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  private Integer counter = 0;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitProducer.class);

  private RabbitTemplate rabbitTemplate;

  public RabbitProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Scheduled(fixedDelay = 5000)
  public void tick() throws Exception {
    Order order = new Order(UUID.randomUUID(), "Product", 23.00);
    Notification notification = new Notification(order.orderId(),
            "Your order with id " + order.orderId() + " has been received");
    sendToQueue(notification);
    sendOrderToMonitor(order);
    sendNotificationToMonitor(notification);
  }

  public void sendToQueue(Notification o){
    LOGGER.info(String.format("Message sent -> %s", o));
    rabbitTemplate.convertAndSend(exchange, routingKey, o);
  }

  @MessageMapping("/chat")
  public void sendOrderToMonitor(@Payload Order order) throws Exception {
    System.out.println("Message sent to client");
    simpMessagingTemplate.convertAndSend("/topic/producer/order", order);
    counter++;
  }

  @MessageMapping("/chat")
  public void sendNotificationToMonitor(@Payload Notification notification) throws Exception {
    System.out.println("Message sent to client");
    simpMessagingTemplate.convertAndSend("/topic/producer/notification", notification);
    counter++;
  }

}
