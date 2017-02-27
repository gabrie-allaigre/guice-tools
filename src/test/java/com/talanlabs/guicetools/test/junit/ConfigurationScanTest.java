package com.talanlabs.guicetools.test.junit;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.talanlabs.guicetools.ConfigurationScanModule;
import com.talanlabs.guicetools.test.data.MyModule;
import com.talanlabs.guicetools.test.data.Tata;
import com.talanlabs.guicetools.test.data.TataModule;
import com.talanlabs.guicetools.test.data.Toto;
import com.talanlabs.guicetools.test.data.TotoModule;
import com.talanlabs.guicetools.test.data.sub.Titi;
import com.talanlabs.guicetools.test.data.sub.TitiModule;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class ConfigurationScanTest {

    @Test
    public void testEmptyConfigurationScan() {
        Injector injector = Guice.createInjector(new ConfigurationScanModule("com.talanlabs.guicetools.test.other"));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNull();
    }

    @Test
    public void testConfigurationConfigurationScan() {
        Injector injector = Guice.createInjector(new ConfigurationScanModule("com.talanlabs.guicetools.test.data"));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNotNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNotNull();
    }

    @Test
    public void testModuleConfigurationScan() {
        Injector injector = Guice.createInjector(new ConfigurationScanModule("com.talanlabs.guicetools.test.data", MyModule.class));
        Assertions.assertThat(injector.getExistingBinding(Key.get(Toto.class))).isNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Tata.class))).isNotNull();
        Assertions.assertThat(injector.getExistingBinding(Key.get(Titi.class))).isNotNull();
    }

    @Test
    public void testOrder1ConfigurationScan() {
        Binder binder = Mockito.mock(Binder.class);
        new ConfigurationScanModule("com.talanlabs.guicetools.test.data").configure(binder);
        InOrder inOrder = Mockito.inOrder(binder);
        inOrder.verify(binder).install(Mockito.refEq(new TitiModule()));
        inOrder.verify(binder).install(Mockito.refEq(new TotoModule()));
    }

    @Test
    public void testOrder2ConfigurationScan() {
        Binder binder = Mockito.mock(Binder.class);
        new ConfigurationScanModule("com.talanlabs.guicetools.test.data", MyModule.class).configure(binder);
        InOrder inOrder = Mockito.inOrder(binder);
        inOrder.verify(binder).install(Mockito.refEq(new TataModule()));
        inOrder.verify(binder).install(Mockito.refEq(new TitiModule()));
    }

    @Test
    public void testFailConfigurationScan() {
        Assertions.assertThatThrownBy(() -> Guice.createInjector(new ConfigurationScanModule("com.talanlabs.guicetools.test.fail"))).hasCauseInstanceOf(IllegalStateException.class);
    }
}
