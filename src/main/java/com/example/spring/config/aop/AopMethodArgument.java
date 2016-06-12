package com.example.spring.config.aop;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author gimbyeongsu
 * 
 */
public class AopMethodArgument {
	private final Annotation[] annotations;
	private final Object value;

	private AopMethodArgument(Annotation[] annotations, Object value) {
		this.annotations = annotations;
		this.value = value;
	}

	public boolean hasAnnotation(Class<? extends Annotation> type) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public Object getValue() {
		return value;
	}

	public static AopMethodArgument[] of(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
		Object[] values = joinPoint.getArgs();
		AopMethodArgument[] arguments = new AopMethodArgument[values.length];
		for (int i = 0, length = values.length; i < length; ++i) {
			arguments[i] = new AopMethodArgument(annotations[i], values[i]);
		}
		return arguments;
	}
}