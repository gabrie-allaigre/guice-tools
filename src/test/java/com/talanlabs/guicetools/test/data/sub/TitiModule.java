package com.talanlabs.guicetools.test.data.sub;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.talanlabs.guicetools.scan.Configuration;
import com.talanlabs.guicetools.scan.Order;
import com.talanlabs.guicetools.test.data.MyModule;

@MyModule
@Configuration
@Order(10)
public class TitiModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    public Titi titi() {
        return new Titi();
    }
}
