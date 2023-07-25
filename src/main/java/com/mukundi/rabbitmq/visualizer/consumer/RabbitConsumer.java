package com.mukundi.rabbitmq.visualizer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(String message){
    try { Thread.sleep(7000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Received message -> %s", message + " from consumer 1"));
  }

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume2(String message){
    try { Thread.sleep(3000);} catch (InterruptedException ignored) {}
    LOGGER.info(String.format("Received message -> %s", message + " from consumer 2"));
  }

}
