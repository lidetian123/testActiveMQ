package com.Jayce.producer;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

@Component("queueSender")
public class QueueSender {

    @Autowired
    @Qualifier("jmsQueueTemplate")
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

    /***
     * 发送Map类型的消息
     * @param queueName
     * @param message
     */
    public void sendMap(String queueName, final MapMessage message) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return message;
            }
        });
    }

    /***
     * 延迟队列（先把消息放在等待区，到时间进队列）
     * @param queueName
     * @param message
     * @param time 毫秒
     */
    public void sendStringWait(String queueName, final String message,final long time) {
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage mes = session.createTextMessage(message);
                // 要想实现mq的延迟加载，必须在mq的安装目录下的conf文件夹的activemq.xml文件中添加 schedulerSupport="true"。调度支持
                /****
                 * 例子：
                 * <broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" schedulerSupport="true">
                 */
                mes.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
                return mes;
            }
        });
    }
}
