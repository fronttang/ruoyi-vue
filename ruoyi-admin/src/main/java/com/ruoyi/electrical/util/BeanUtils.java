package com.ruoyi.electrical.util;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtils {
	
	public static <T> T replaceNullWithSlash(T bean) {
        if (bean == null) return null;
        try {
            Class<?> clazz = bean.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(bean);
                if (value == null && field.getType() == String.class) {
                    field.set(bean, "/");
                }
            }
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
        return bean;
    }
}
