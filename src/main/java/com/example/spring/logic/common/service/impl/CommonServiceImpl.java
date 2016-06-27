package com.example.spring.logic.common.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.example.spring.config.InMemoryRefresh;
import com.example.spring.logic.common.service.CommonService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class CommonServiceImpl implements CommonService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Resource
	private List<ActiveMQQueue> serverNameQueueList;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private List<InMemoryRefresh> inMemoryRefreshList;

	@Override
	public void startInMemoryRefresh(final String cacheName) {
		for (ActiveMQQueue queue : serverNameQueueList) {
			LOGGER.debug("{}", queue);
			jmsTemplate.send(queue, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createTextMessage(cacheName);
					message.setJMSCorrelationID(UUID.randomUUID().toString());
					message.setJMSType("inMemoryRefresh");
					return message;
				}
			});
		}
	}

	@Override
	public void inMemoryRefresh(String cacheName) {
		for (InMemoryRefresh each : inMemoryRefreshList) {
			if ("all".equals(cacheName)) {
				each.refresh();
			} else if (each.getInMemoryRefreshName().equals(cacheName)) {
				each.refresh();
			} else {
				LOGGER.warn("{}", cacheName);
			}
		}
	}
}
