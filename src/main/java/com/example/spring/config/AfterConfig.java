package com.example.spring.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.example.spring.batch.Batch;

/**
 * @author gimbyeongsu
 * 
 */
@Profile({ "local", "svc.01", "svc.02" })
@Configuration
@DependsOn(value = { "rootConfig", "jdbcConfig", "redisConfig", "cacheConfig", "schedulingConfig",
		"delegatingWebMvcConfig", "webMvcConfig" })
public class AfterConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AfterConfig.class);

	@Autowired
	private List<Batch> batchs;
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	public AfterConfig() {
		LOGGER.debug("생성자 AfterConfig()");
	}

	@PostConstruct
	public void start() {
		// batch start
		for (Batch task : batchs) {
			if (task.isEnabled()) {
				threadPoolTaskScheduler.schedule(task, new CronTrigger(task.getExpression()));
			} else {
				LOGGER.info("{} 배치는 사용하지 않습니다.", task.getName());
			}
		}
	}

	@PreDestroy
	public void shutdown() {
		while (true) {
			Map<String, Boolean> map = new HashMap<>();
			for (Batch task : batchs) {
				task.shutdownEnabled();
				map.put(task.getName(), task.isInProgress());
			}
			List<Integer> doneList = new ArrayList<>();
			for (String key : map.keySet()) {
				if (map.get(key) == false) {
					doneList.add(0);
				} else {
					LOGGER.debug("{} 배치가 아직 실행 중입니다.", key);
				}
			}
			if (doneList.size() == batchs.size()) {
				LOGGER.debug("실행 중인 배치가 없습니다.");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	@Bean
	public AppContextAware appContextAware() {
		return new AppContextAware();
	}
}
