package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.example.spring.logic.jms.QueueMessageListener;
import com.example.spring.logic.jms.ServerQueueMessageListener;
import com.example.spring.logic.jms.TopicMessageListener;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * @author gimbyeongsu
 *
 */
@Configuration
@DependsOn(value = { "rootConfig" })
public class ActiveMQConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfig.class);

	@Autowired
	private Environment environment;

	public ActiveMQConfig() {
		LOGGER.debug("생성자 ActiveMQConfig()");
	}

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		String brokerURL = "nio://127.0.0.1:61616?wireFormat.tightEncodingEnabled=false";
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
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

	@Bean(name = "serverNameQueueList")
	public List<ActiveMQQueue> serverNameQueueList() {
		List<String> serverNameList = Splitter.on(CharMatcher.anyOf(",")).trimResults().omitEmptyStrings()
				.splitToList(environment.getRequiredProperty("server.name.list"));
		List<ActiveMQQueue> queueList = new ArrayList<>();
		for (String serverName : serverNameList) {
			ActiveMQQueue queue = new ActiveMQQueue(serverName);
			queueList.add(queue);
		}
		return queueList;
	}

	@Bean
	public ServerQueueMessageListener serverQueueMessageListener() {
		ServerQueueMessageListener messageListener = new ServerQueueMessageListener();
		return messageListener;
	}

	@Bean
	public DefaultMessageListenerContainer serverQueueMessageListenerContainer() {
		// server to server push
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory());
		ActiveMQQueue queue = new ActiveMQQueue(getProfile());
		listener.setDestination(queue);
		listener.setMessageListener(serverQueueMessageListener());
		return listener;
	}

	private String getProfile() {
		String[] profiles = environment.getActiveProfiles();
		String profile = profiles[0];
		return profile;
	}
}
