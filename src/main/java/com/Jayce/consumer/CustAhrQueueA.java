package com.Jayce.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@Service
public class CustAhrQueueA implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(CustAhrQueueA.class);

    @Override
    public void onMessage(Message message)  {

        if (message instanceof TextMessage) {

            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("队列消费者A:取得消息"+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
