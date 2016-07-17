package com.example.app.junit;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.AuditAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementServerPropertiesAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.PublicMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceRepositoryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceWebFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.config.DelegatingWebMvcConfig;
import com.example.spring.config.WebAppContextConfig;
import com.example.spring.config.WebMvcConfig;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedServletContainerAutoConfiguration.class, //
		DispatcherServletAutoConfiguration.class, //
		ErrorMvcAutoConfiguration.class, //
		CassandraAutoConfiguration.class, //
		CassandraDataAutoConfiguration.class, //
		CassandraRepositoriesAutoConfiguration.class, //
		WebSocketMessagingAutoConfiguration.class, //
		SpringDataWebAutoConfiguration.class, //
		JmsAutoConfiguration.class, //
		ServerPropertiesAutoConfiguration.class, //
		WebSocketAutoConfiguration.class, //
		SecurityAutoConfiguration.class, //
		JacksonAutoConfiguration.class, //
		DataSourceAutoConfiguration.class, //
		HttpMessageConvertersAutoConfiguration.class, //
		JmxAutoConfiguration.class, //
		SpringApplicationAdminJmxAutoConfiguration.class, //
		AopAutoConfiguration.class, //
		JtaAutoConfiguration.class, //
		DataSourceTransactionManagerAutoConfiguration.class, //
		ConfigurationPropertiesAutoConfiguration.class, //
		WebMvcAutoConfiguration.class, //
		HttpEncodingAutoConfiguration.class, //
		MultipartAutoConfiguration.class, //
		AuditAutoConfiguration.class, //
		MetricRepositoryAutoConfiguration.class, //
		PublicMetricsAutoConfiguration.class, //
		HealthIndicatorAutoConfiguration.class, //
		EndpointAutoConfiguration.class, //
		ManagementServerPropertiesAutoConfiguration.class, //
		EndpointWebMvcAutoConfiguration.class, //
		MetricFilterAutoConfiguration.class, //
		MetricExportAutoConfiguration.class, //
		MetricsDropwizardAutoConfiguration.class, //
		TraceRepositoryAutoConfiguration.class, //
		TraceWebFilterAutoConfiguration.class //
})
@ComponentScan(basePackages = { "com.example.spring" }, excludeFilters = {
		@Filter(value = { WebAppContextConfig.class, WebMvcConfig.class, DelegatingWebMvcConfig.class }, type = FilterType.ASSIGNABLE_TYPE),
		@Filter(value = { RestController.class, Controller.class }, type = FilterType.ANNOTATION),
		@Filter(pattern = { "com.example.spring.*.model.*" }, type = FilterType.REGEX) })
@DependsOn(value = { "rootConfig", "jdbcConfig", "redisConfig", "cacheConfig", "schedulingConfig", "activeMQConfig",
		"cassandraConfig" })
public class JunitConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(JunitConfig.class);

	public JunitConfig() {
		LOGGER.debug("생성자 JunitConfig()");
	}

	@Bean
	public Map<String, Object> configurationBeanInfo(ApplicationContext applicationContext) throws Exception {
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(Configuration.class);
		for (String key : map.keySet()) {
			LOGGER.debug("{} {}", key, map.get(key));
		}

		LOGGER.debug("");
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			LOGGER.debug("{}", beanName);
		}
		return map;
	}
}