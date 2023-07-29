# RabbitMQ Demo with Spring Boot and React JS

This project shows how to use rabbitmq as a message broker between a producer and a consumer in spring boot. The producer creates orders and sends notifications to be sent by the notification service, which acts as the consumer. The project also has a front end built in React JS, which receives orders and notifications via a tcp connection and displays them on the screen.

## Features

- Spring Boot application that acts as a producer of orders and notifications
- Spring Boot application that acts as a consumer of notifications and sends them via email
- RabbitMQ server that acts as a message broker between the producer and the consumer
- React JS application that acts as a front end for displaying orders, notifications, and queue status
- Spring STOMP websocket for real time updates to the react application

## Requirements

- Java 17 or higher
- Maven
- RabbitMQ
- Node.js

## Installation

1. Clone this repository: `git clone https://github.com/MukundiCode/rabbitmq-visualizer.git`
3. Install the dependencies for the spring boot applications: `mvn install`
4. Install the dependencies for the React JS application: `cd frontend && npm install`
5. Configure the application.properties files for the producer and the consumer applications with your RabbitMQ server details and email credentials
6. Start the RabbitMQ server
8. Start the application: `mvn spring-boot:run`
10. Start the React JS application: `cd frontend && npm start`

## Usage

- The producer application will create orders every 10 seconds and send them to the RabbitMQ server
- The producer application will also send notifications for each order to be sent by the consumer application
- The consumer application will receive notifications from the RabbitMQ server and process them
- The TCP server will receive orders and notifications from the spring boot applications and send them to the React JS application
- The React JS application will display the orders, notifications, and queue status on the screen

## License

This project is licensed under the MIT License - see the [LICENSE] file for details.
