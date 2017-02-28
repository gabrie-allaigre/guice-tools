package com.talanlabs.guicetools.test.junit;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.talanlabs.guicetools.scan.ComponentScanModule;
import com.talanlabs.guicetools.test.data.Tata;
import com.talanlabs.guicetools.test.data.Toto;
import com.talanlabs.guicetools.test.data.sub.Titi;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class ComponentScanTest {

    @Test
    public void testEmptyComponentScan() {
        Injector injector = Guice.createInjector(new ComponentScanModule("com.talanlabs.guicetools.test.other"));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNull();
    }

    @Test
    public void testComponentComponentScan() {
        Injector injector = Guice.createInjector(new ComponentScanModule("com.talanlabs.guicetools.test.data"));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNotNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNotNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNull();
    }

    @Test
    public void testSingletonScan() {
        Injector injector = Guice.createInjector(new ComponentScanModule("com.talanlabs.guicetools.test.data", Singleton.class));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNotNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNotNull();
    }

    @Test
    public void testOrder1ComponentScan() {
        Binder binder = Mockito.mock(Binder.class);
        new ComponentScanModule("com.talanlabs.guicetools.test.data").configure(binder);
        InOrder inOrder = Mockito.inOrder(binder);
        inOrder.verify(binder).bind(Mockito.eq(Titi.class));
        inOrder.verify(binder).bind(Mockito.eq(Toto.class));
    }

    @Test
    public void testOrder2ComponentScan() {
        Binder binder = Mockito.mock(Binder.class);
        new ComponentScanModule("com.talanlabs.guicetools.test.data", Singleton.class).configure(binder);
        InOrder inOrder = Mockito.inOrder(binder);
        inOrder.verify(binder).bind(Mockito.eq(Tata.class));
        inOrder.verify(binder).bind(Mockito.eq(Titi.class));
    }
}
