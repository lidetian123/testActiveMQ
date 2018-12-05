package com.Jayce.producer;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

@Component("queueSender")
public class QueueSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String queueName, final Object message) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage mes = session.createObjectMessage((Serializable) message);
                return mes;
            }
        });
    }

    public void sendString(String queueName, final String message) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage mes = session.createTextMessage(message);
                return mes;
            }
        });
    }

    public void sendStringWait(String queueName, final String message,final long time) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage mes = session.createTextMessage(message);
                mes.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
                return mes;
            }
        });
    }
}
