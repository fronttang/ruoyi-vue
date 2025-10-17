package com.ruoyi.electrical.util;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtils {
	
	public static <T> T replaceNullWithSlash(T bean) {
		if (bean == null) return null;
	    try {
	        Class<?> clazz = bean.getClass();
	        while (clazz != null) {
	            for (Field field : clazz.getDeclaredFields()) {
	                if (field.getType() == String.class) {
	                    field.setAccessible(true);
	                    Object value = field.get(bean);
	                    if (value == null || "".equals(value)) {
	                        field.set(bean, "/");
	                        log.debug("Set field '{}' to '/'", field.getName());
	                    }
	                }
	            }
	            clazz = clazz.getSuperclass();
	        }
	    } catch (Exception e) {
	        log.error("Error replacing null or empty string with slash", e);
	    }
	    return bean;
    }
}
