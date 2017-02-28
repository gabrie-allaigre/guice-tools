package com.talanlabs.guicetools.test.data.sub;

import com.google.inject.Singleton;
import com.talanlabs.guicetools.scan.Component;
import com.talanlabs.guicetools.scan.Order;

@Component
@Singleton
@Order(10)
public class Titi {
}
