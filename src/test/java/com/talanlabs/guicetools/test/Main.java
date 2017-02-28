package com.talanlabs.guicetools.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.talanlabs.guicetools.logger.InjectLogger;
import com.talanlabs.guicetools.logger.LoggerModuler;
import org.slf4j.Logger;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new LoggerModuler(), new AbstractModule() {
			@Override
			protected void configure() {
				bind(Gaby.class);
			}
		});


		Gaby gaby = injector.getInstance(Gaby.class);
		gaby.show();
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
