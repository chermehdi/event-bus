package io.github.chermehdi.guavabus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to annotate method which we wants to be invokable by the EventBus identified by its value
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
