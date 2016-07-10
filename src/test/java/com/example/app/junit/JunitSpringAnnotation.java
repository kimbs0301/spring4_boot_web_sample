package com.example.app.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.spring.config.ActiveMQConfig;
import com.example.spring.config.CacheConfig;
import com.example.spring.config.CassandraConfig;
import com.example.spring.config.JdbcConfig;
import com.example.spring.config.RedisConfig;
import com.example.spring.config.RootConfig;
import com.example.spring.config.SchedulingConfig;

/**
 * @author gimbyeongsu
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringApplicationConfiguration(classes = { JunitConfig.class, RootConfig.class, JdbcConfig.class, RedisConfig.class,
		CacheConfig.class, SchedulingConfig.class, ActiveMQConfig.class, CassandraConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "junit" })
@TestPropertySource(locations = "classpath:application-junit.properties")
public @interface JunitSpringAnnotation {

}
