package com.talanlabs.guicetools.test.data.sub;

import com.google.inject.Singleton;
import com.talanlabs.guicetools.Component;
import com.talanlabs.guicetools.Order;

@Component
@Singleton
@Order(10)
public class Titi {
}
