package com.zero.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zero on 16/3/1.
 */
public class ReflectionUtils {

	private static Logger LOG= LoggerFactory.getLogger(ReflectionUtils.class);
	public static <T> Class<T> getSuperClassGenricType(final Class clazz){
		return getSuperClassGenricType(clazz,0);
	}

	public static Class getSuperClassGenricType(Class clazz,int index){
		Type genType=clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)){
			LOG.warn(clazz.getSimpleName()+"'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params=((ParameterizedType)genType).getActualTypeArguments();
		if(index>=params.length||index<0){
			LOG.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			LOG.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	public static Object getFieldValue(Object obj, String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			LOG.error("impossible exception {}", e.getMessage());
		}
		return result;
	}

	private static Field getAccessibleField(Object obj, String fieldName) {
		Assert.notNull(obj, "object can not be null!");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {

			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static void setFieldValue(Object obj, String fieldName, List value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			LOG.error("impossible exception {}", e.getMessage());
		}
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
