package com.talanlabs.guicetools.test.data;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

@MyModule
public class TataModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    public Tata tata() {
        return new Tata();
    }
}
