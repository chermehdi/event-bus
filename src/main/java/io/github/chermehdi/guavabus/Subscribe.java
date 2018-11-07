package io.github.chermehdi.guavabus;

import java.lang.annotation.*;

/**
 * Annotation to annotate methode which we wants to be invocable
 * by the EventBus identified by its value
 *
 * @author chermehdi
 */
@Repeatable(Subscribe.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
    String value() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface List {
        Subscribe[] value();
    }
}
