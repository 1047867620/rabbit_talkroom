package com.company;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

public class Customer {
    public final static String EXCHANGE_NAME = "Hello";

    public static void customer() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.108.159.221");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}