package com.talanlabs.guicetools.scan;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.talanlabs.java.lambda.Try;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class ConfigurationScanModule extends AbstractModule {

    private final String packageName;
    private final Set<Class<? extends Annotation>> installAnnotations;

    public ConfigurationScanModule(String packageName) {
        this(packageName, Configuration.class);
    }

    @SafeVarargs
    public ConfigurationScanModule(String packageName, final Class<? extends Annotation>... installAnnotations) {
        super();

        this.packageName = packageName;
        this.installAnnotations = Sets.newHashSet(installAnnotations);
    }

    @Override
    public void configure() {
        Reflections packageReflections = new Reflections(packageName);
        Try<List<Module>> ms = installAnnotations.stream().map(packageReflections::getTypesAnnotatedWith).flatMap(Set::stream).filter(Module.class::isAssignableFrom).sorted(this::orderClass)
                .map(Try.lazyOf(Class::newInstance)).map(Try.lazyOf(o -> (Module) o.get().getOrThrow())).collect(Try.collect());
        try {
            ms.getOrThrow().forEach(this::install);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to install module", e);
        }
    }

    private int orderClass(Class<?> a, Class<?> b) {
        int va = a.isAnnotationPresent(Order.class) ? a.getAnnotation(Order.class).value() : Integer.MAX_VALUE;
        int vb = b.isAnnotationPresent(Order.class) ? b.getAnnotation(Order.class).value() : Integer.MAX_VALUE;
        return Integer.compare(va, vb);
    }
}