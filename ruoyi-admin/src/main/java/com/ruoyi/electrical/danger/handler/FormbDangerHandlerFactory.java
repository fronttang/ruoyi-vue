package com.ruoyi.electrical.danger.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import cn.hutool.core.collection.CollUtil;

@Component
public class FormbDangerHandlerFactory implements ApplicationContextAware {

	private static final ConcurrentHashMap<String, IFormbDangerHandler> HANDLER_MAP = new ConcurrentHashMap<String, IFormbDangerHandler>();

	public static IFormbDangerHandler getFormbDangerHander(String type) {
		return HANDLER_MAP.get(type);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		Map<String, IFormbDangerHandler> handerBeans = applicationContext.getBeansOfType(IFormbDangerHandler.class);

		if (CollUtil.isNotEmpty(handerBeans)) {
			handerBeans.forEach((name, bean) -> {
				FormbDangerHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(),
						FormbDangerHandler.class);
				if (annotation != null && annotation.value() != null) {
					HANDLER_MAP.put(annotation.value(), bean);
				}
			});
		}
	}

}
