package com.example.spring.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.example.spring.logic.jms.QueueMessageListener;
import com.example.spring.logic.jms.TopicMessageListener;

/**
 * @author gimbyeongsu
 *
 */
@Configuration
@DependsOn(value = { "rootConfig" })
public class ActiveMQConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfig.class);

	public ActiveMQConfig() {
		LOGGER.debug("생성자 ActiveMQConfig()");
	}

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("nio://127.0.0.1:61616");
		connectionFactory.setUserName("seban21");
		connectionFactory.setPassword("!aA123456");
		return connectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
		return cachingConnectionFactory;
	}

	@Bean(name = "defaultQueue")
	public ActiveMQQueue defaultQueue() {
		ActiveMQQueue queue = new ActiveMQQueue("DefaultQueue");
		return queue;
	}

	@Bean(name = "defaultTopic")
	public ActiveMQTopic defaultTopic() {
		ActiveMQTopic topic = new ActiveMQTopic("DefaultTopic");
		return topic;
	}

	@Bean(name = "recvQueue")
	public ActiveMQQueue recvQueue() {
		ActiveMQQueue queue = new ActiveMQQueue("RecvQueue");
		return queue;
	}

	@Bean(name = "ackQueue")
	public ActiveMQQueue ackQueue() {
		ActiveMQQueue queue = new ActiveMQQueue("AckQueue");
		return queue;
	}

	@Bean
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
		jmsTemplate.setDefaultDestination(defaultQueue());
		return jmsTemplate;
	}

	@Bean
	public QueueMessageListener queueMessageListener() {
		QueueMessageListener messageListener = new QueueMessageListener();
		return messageListener;
	}

	@Bean
	public TopicMessageListener topicMessageListener() {
		TopicMessageListener messageListener = new TopicMessageListener();
		return messageListener;
	}

	@Bean
	public DefaultMessageListenerContainer queueMessageListenerContainer() {
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory());
		listener.setDestination(recvQueue());
		listener.setMessageListener(queueMessageListener());
		return listener;
	}

	@Bean
	public DefaultMessageListenerContainer topicMessageListenerContainer() {
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory());
		listener.setDestination(defaultTopic());
		listener.setMessageListener(topicMessageListener());
		return listener;
	}
}
