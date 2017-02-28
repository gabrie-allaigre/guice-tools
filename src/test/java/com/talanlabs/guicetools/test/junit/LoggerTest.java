package com.talanlabs.guicetools.test.junit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.talanlabs.guicetools.logger.InjectLogger;
import com.talanlabs.guicetools.logger.LoggerModuler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;

public class LoggerTest {

	@Test
	public void testLogger1() {
		Injector injector = Guice.createInjector(new LoggerModuler(), new AbstractModule() {
			@Override
			protected void configure() {
				bind(Gaby.class);
			}
		});
		Gaby gaby = injector.getInstance(Gaby.class);

		Assertions.assertThat(gaby.logger1).isNotNull().extracting(Logger::getName)
				.contains("com.talanlabs.guicetools.test.junit.LoggerTest$Gaby");
		Assertions.assertThat(gaby.logger2).isNotNull().extracting(Logger::getName)
				.contains("com.talanlabs.guicetools.test.junit.LoggerTest$Gaby.gaby");
		Assertions.assertThat(gaby.logger3).isNotNull().extracting(Logger::getName).contains("gaby");
		Assertions.assertThat(gaby.logger4).isNotNull().extracting(Logger::getName)
				.contains("com.talanlabs.guicetools.test.junit.LoggerTest$Gaby");
	}

	public static class Gaby {

		@InjectLogger
		Logger logger1;

		@InjectLogger("gaby")
		Logger logger2;

		@InjectLogger(value = "gaby", absolute = true)
		Logger logger3;

		Logger logger4;

		@InjectLogger
		public void setLogger4(Logger logger4) {
			this.logger4 = logger4;
		}

		public void show() {
			logger1.info("test1");
			logger2.info("test2");
			logger3.info("test3");
			logger4.info("test4");
		}
	}
}
