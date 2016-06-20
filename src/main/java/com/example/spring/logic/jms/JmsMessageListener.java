package com.example.spring.logic.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * @author gimbyeongsu
 * 
 */
public class JmsMessageListener implements SessionAwareMessageListener<TextMessage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageListener.class);

	public JmsMessageListener() {
	}

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		LOGGER.debug("{}", message.getJMSMessageID());
		LOGGER.debug("{}", message.getText());

		ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("ack message");

		MessageProducer producer = session.createProducer(message.getJMSReplyTo());
		producer.send(textMessage);
	}
}