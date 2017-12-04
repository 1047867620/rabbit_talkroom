package com.company;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public final static String EXCHANGE_NAME = "Hello";

    public static void producer() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.108.159.221");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "s");

        System.out.println("欢迎加入群聊，请输入您的昵称：");
        String username = new Scanner(System.in).nextLine();
        System.out.println("Hello "+username+",输入exit退出！");
        while (true){
            String message = new Scanner(System.in).nextLine();
            if("exit".equals(message)){
                channel.close();
                connection.close();
                return;
            }
            message = username +":"+message;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        }
    }
}

