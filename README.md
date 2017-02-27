# Guice Tools

Add auto scan component and configuration for Guice

```xml
<dependencies>
    <dependency>
        <groupId>com.talanlabs</groupId>
        <artifactId>guice-tools</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage

### Component

By default add `@Component` in bind class

```java
@Component
public class MyExample {
    ...
}
```

And install scan module in guice module

```java
install(new ComponentScanModule("com.example"));
```

Or change scan with `@Singleton`

```java
install(new ComponentScanModule("com.example",Singleton.class));
```

### Configuration

By defualt add `@Configuration` in install module

```java
@Configuration
public class MyModule extends AbstractModule {
    ...
}
```

And install scan module in guice module

```java
install(new ConfigurationScanModule("com.example"));
```