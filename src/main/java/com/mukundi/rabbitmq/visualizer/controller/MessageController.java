package com.mukundi.rabbitmq.visualizer.controller;

import com.mukundi.rabbitmq.visualizer.publisher.RabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

  private RabbitProducer producer;

  private Integer counter = 0;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  public MessageController(RabbitProducer producer) {
    this.producer = producer;
  }

  @GetMapping("/publish")
  public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
    producer.sendMessage(message);
//    try {
//      send("Produced");
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
    return ResponseEntity.ok("Message sent to RabbitMQ ...");
  }

//  @Scheduled(fixedDelay = 5000)
//  public void tick() throws Exception {
//    send("Produced");
//  }
//
//  @MessageMapping("/socket")
//  @SendTo("/topic/messages")
//  public void send(String message) throws Exception {
//    System.out.println("Sending");
//    simpMessagingTemplate.convertAndSend("/topic/messages", message + counter);
//    counter++;
//  }

}
