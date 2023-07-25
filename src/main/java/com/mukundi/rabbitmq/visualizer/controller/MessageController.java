package com.mukundi.rabbitmq.visualizer.controller;

import com.mukundi.rabbitmq.visualizer.publisher.RabbitProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class MessageController {

  private RabbitProducer producer;

  public MessageController(RabbitProducer producer) {
    this.producer = producer;
  }

  @GetMapping("/publish")
  public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
    producer.sendMessage(message);
    try {
      send("Produced");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.ok("Message sent to RabbitMQ ...");
  }

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public String send(String message) throws Exception {
    return message;
  }

}
