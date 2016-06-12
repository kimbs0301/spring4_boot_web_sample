package com.example.spring.config.aop;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.spring.logic.member.model.Member;

/**
 * @author gimbyeongsu
 * 
 */
@Component
@Aspect
public class ParamAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParamAspect.class);

	public ParamAspect() {
		LOGGER.debug("생성자 ParamAspect()");
	}

	@Before("execution(* *(.., @com.example.spring.config.aop.ParamAop (*), ..))")
	public void settingParam(JoinPoint joinPoint) {
		LOGGER.debug("");
		for (AopMethodArgument argument : AopMethodArgument.of(joinPoint)) {
			if (argument.hasAnnotation(ParamAop.class)) {
				LOGGER.debug("");
				Member member = (Member) argument.getValue();
				member.setName(UUID.randomUUID().toString());
			}
		}
	}

	@After("execution(* *(.., @com.example.spring.config.aop.ParamAop (*), ..))")
	public void afterParam() {
		LOGGER.debug("");
	}
}