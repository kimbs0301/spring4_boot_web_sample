package com.example.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author gimbyeongsu
 * 
 */
@Component
public class TestBatch implements Batch {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBatch.class);

	private boolean inProgress;
	private boolean shutdown = false;

	@Autowired
	private Environment environment;

	public TestBatch() {
		LOGGER.debug("생성자 TestBatch()");
	}

	@Override
	public String getName() {
		return "TEST";
	}

	@Override
	public String getExpression() {
		return "1/3 * * * * ?";
	}

	@Override
	public boolean isInProgress() {
		return inProgress;
	}

	@Override
	public void shutdownEnabled() {
		shutdown = true;
	}

	@Override
	public boolean isEnabled() {
		return environment.getRequiredProperty("batch.test.enabled", Boolean.class);
	}

	@Override
	public void run() {
		if (shutdown || !isEnabled()) {
			return;
		}

		inProgress = true;

		LOGGER.debug("[{}] test start", getName());
		try {
			// work
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		LOGGER.debug("[{}] test end", getName());

		inProgress = false;
	}
}
