package com.example.spring.logic.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.example.spring.logic.common.service.CommonService;

/**
 * @author gimbyeongsu
 * 
 */
public class ServerQueueMessageListener implements SessionAwareMessageListener<TextMessage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerQueueMessageListener.class);

	@Autowired
	private CommonService commonService;

	public ServerQueueMessageListener() {
		LOGGER.debug("생성자 ServerQueueMessageListener()");
	}

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		LOGGER.debug("JMSMessageID:{}", message.getJMSMessageID());
		LOGGER.debug("JMSCorrelationID:{}", message.getJMSCorrelationID());
		LOGGER.debug("JMSDeliveryMode:{}", message.getJMSDeliveryMode());
		LOGGER.debug("JMSExpiration:{}", message.getJMSExpiration());
		LOGGER.debug("JMSTimestamp:{}", message.getJMSTimestamp());
		LOGGER.debug("JMSType:{}", message.getJMSType());
		LOGGER.debug("{}", message.getText());

		if ("inMemoryCacheRefresh".equals(message.getJMSType())) {
			String cacheName = message.getText();

			// CommonService commonService = AppContextAware.getAppCtx().getBean("commonServiceImpl", CommonService.class);
			commonService.inMemoryCacheRefresh(cacheName);
		}
	}
}