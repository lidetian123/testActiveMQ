package com.Jayce.producer;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class TopicSender {

    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;

    public void sendStringWait(String topicName, final String message,final long time) {
        jmsTemplate.send(topicName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage mes = session.createTextMessage(message);
                mes.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
                return mes;
            }
        });
    }
}
