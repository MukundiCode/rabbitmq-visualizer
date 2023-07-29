package com.mukundi.rabbitmq.visualizer.dto;

import java.util.UUID;

public record Order(UUID orderId,
                    String productName,
                    Double price) {}
