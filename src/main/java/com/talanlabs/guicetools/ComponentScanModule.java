package com.talanlabs.guicetools;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Scan package and sub package, bind all classes with type annotation.
 * <p>
 * Default is @Component annotation
 */
public class ComponentScanModule extends AbstractModule {

    private final String packageName;
    private final Set<Class<? extends Annotation>> bindingAnnotations;

    /**
     * Scan package and sub package, bind all classes with @Component annotation.
     *
     * @param packageName package to scan
     */
    public ComponentScanModule(String packageName) {
        this(packageName, Component.class);
    }

    /**
     * Scan package and sub package, bind all classes with type annotation
     *
     * @param packageName        package to scan
     * @param bindingAnnotations type annotations to bind
     */
    @SafeVarargs
    public ComponentScanModule(String packageName, final Class<? extends Annotation>... bindingAnnotations) {
        super();

        this.packageName = packageName;
        this.bindingAnnotations = Sets.newHashSet(bindingAnnotations);
    }

    @Override
    public void configure() {
        Reflections packageReflections = new Reflections(packageName);
        bindingAnnotations.stream().map(packageReflections::getTypesAnnotatedWith).flatMap(Set::stream).sorted(this::orderClass).forEach(this::bind);
    }

    private int orderClass(Class<?> a, Class<?> b) {
        int va = a.isAnnotationPresent(Order.class) ? a.getAnnotation(Order.class).value() : Integer.MAX_VALUE;
        int vb = b.isAnnotationPresent(Order.class) ? b.getAnnotation(Order.class).value() : Integer.MAX_VALUE;
        return Integer.compare(va, vb);
    }
}