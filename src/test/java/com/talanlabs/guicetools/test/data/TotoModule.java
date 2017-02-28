package com.talanlabs.guicetools.test.data;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.talanlabs.guicetools.scan.Configuration;

@Configuration
public class TotoModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    public Toto toto() {
        return new Toto();
    }
}
