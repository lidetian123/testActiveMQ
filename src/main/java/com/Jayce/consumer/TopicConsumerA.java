package com.Jayce.consumer;

import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
public class TopicConsumerA implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try{
            System.out.println("主题消费者A:取得消息"+textMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
