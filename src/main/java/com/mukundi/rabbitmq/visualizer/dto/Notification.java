package com.mukundi.rabbitmq.visualizer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record Notification(UUID orderId,
                           String message,
                           LocalDateTime dateTime) {
}
