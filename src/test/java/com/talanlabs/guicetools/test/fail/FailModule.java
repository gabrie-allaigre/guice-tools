package com.talanlabs.guicetools.test.fail;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.talanlabs.guicetools.scan.Configuration;
import com.talanlabs.guicetools.test.data.Toto;

@Configuration
public class FailModule extends AbstractModule {

    public FailModule(String text) {
    }

    @Override
    protected void configure() {
    }

    @Provides
    public Toto toto() {
        return new Toto();
    }
}
