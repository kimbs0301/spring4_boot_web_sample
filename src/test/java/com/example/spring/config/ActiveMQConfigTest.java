package com.example.spring.config;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.app.junit.JunitConfig;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JunitConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "junit" })
@TestPropertySource(locations = "classpath:application-junit.properties")
public class ActiveMQConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfigTest.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	@Qualifier("defaultQueue")
	@Autowired
	private ActiveMQQueue defaultQueue;
	@Qualifier("defaultTopic")
	@Autowired
	private ActiveMQTopic defaultTopic;
	@Qualifier("recvQueue")
	@Autowired
	private ActiveMQQueue recvQueue;
	@Qualifier("ackQueue")
	@Autowired
	private ActiveMQQueue ackQueue;

	@Ignore
	@Test
	public void test_echo() throws Exception {
		for (int i = 0; i < 100; ++i) {
			String text = UUID.randomUUID().toString();
			jmsTemplate.send(defaultQueue, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createTextMessage(text);
					message.setJMSCorrelationID(UUID.randomUUID().toString());
					message.setJMSType("TEXT");
					return message;
				}
			});

			LOGGER.debug("send {}", text);
			
			Message message = jmsTemplate.receive(defaultQueue);
			
			LOGGER.debug("JMSMessageID:{}", message.getJMSMessageID());
			LOGGER.debug("JMSCorrelationID:{}", message.getJMSCorrelationID());
			LOGGER.debug("JMSDeliveryMode:{}", message.getJMSDeliveryMode());
			LOGGER.debug("JMSExpiration:{}", message.getJMSExpiration());
			LOGGER.debug("JMSTimestamp:{}", message.getJMSTimestamp());
			LOGGER.debug("JMSType:{}", message.getJMSType());
		}
	}

	@Ignore
	@Test
	public void test_send_recv() throws Exception {
		for (int i = 0; i < 100; ++i) {
			String text = UUID.randomUUID().toString();
			jmsTemplate.send(recvQueue, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createTextMessage(text);
					message.setJMSReplyTo(ackQueue);
					return message;
				}
			});

			LOGGER.debug("send {}", text);
			Message message = jmsTemplate.receive(ackQueue);
			LOGGER.debug("{}", message.getJMSMessageID());
		}
	}

	@Ignore
	@Test
	public void test_topic() throws Exception {
		for (int i = 0; i < 1000000; ++i) {
			String text = UUID.randomUUID().toString();
			jmsTemplate.send(defaultTopic, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createTextMessage(text);
					return message;
				}
			});

			LOGGER.debug("send {}", text);
		}
	}
}
