package com.talanlabs.guicetools.logger;

import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Module to manage @InjectLogger
 */
public class LoggerModuler extends AbstractModule {

	@Override
	protected void configure() {
		bindListener(Matchers.any(), new LoggerTypeListener());
	}

	private class LoggerTypeListener implements TypeListener {

		public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
			Class<?> clazz = typeLiteral.getRawType();
			while (clazz != null) {
				for (Field field : clazz.getDeclaredFields()) {
					if (field.getType() == Logger.class && field.isAnnotationPresent(InjectLogger.class)) {
						typeEncounter.register(
								new LoggerFieldMembersInjector<T>(createLogger(field.getDeclaringClass(),
								                                               field.getAnnotation(InjectLogger.class)),
								                                  field));
					}
				}
				for (Method method : clazz.getDeclaredMethods()) {
					if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == Logger.class &&
							method.isAnnotationPresent(InjectLogger.class)) {
						typeEncounter.register(
								new LoggerMethodMembersInjector<T>(createLogger(method.getDeclaringClass(),
								                                                method.getAnnotation(
										                                                InjectLogger.class)),
								                                   method));
					}
				}
				clazz = clazz.getSuperclass();
			}
		}
	}

	private Logger createLogger(Class<?> clazz, InjectLogger injectLogger) {
		if ("".equals(injectLogger.value().trim())) {
			return LoggerFactory.getLogger(clazz);
		} else {
			String name = injectLogger.value();
			if (!injectLogger.absolute()) {
				name = clazz.getName() + "." + name;
			}
			return LoggerFactory.getLogger(name);
		}
	}

	private class LoggerFieldMembersInjector<T> implements MembersInjector<T> {

		private final Logger logger;
		private final Field field;

		private LoggerFieldMembersInjector(Logger logger, Field field) {
			super();

			this.logger = logger;
			this.field = field;
			field.setAccessible(true);
		}

		public void injectMembers(T t) {
			try {
				field.set(t, logger);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Failed to set field " + field, e);
			}
		}
	}

	private class LoggerMethodMembersInjector<T> implements MembersInjector<T> {

		private final Logger logger;
		private final Method method;

		private LoggerMethodMembersInjector(Logger logger, Method method) {
			super();

			this.logger = logger;
			this.method = method;
			method.setAccessible(true);
		}

		public void injectMembers(T t) {
			try {
				method.invoke(t, logger);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("Failed to invoke method " + method, e);
			}
		}
	}
}
