package io.github.chermehdi.guavabus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * wrapper class holding a pair of method and a target object, the only purpose is storing them as
 * invocable values to be used inside the EventBus
 *
 * @author chermehdi
 */
public final class Invocation {

  private final Method handler;

  private final Object targetObject;

  public Invocation(Method handler, Object targetObject) {
    this.handler = handler;
    this.targetObject = targetObject;
  }

  public Method getHandler() {
    return handler;
  }

  public Object getTargetObject() {
    return targetObject;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Invocation that = (Invocation) o;
    return Objects.equals(handler, that.handler) &&
        Objects.equals(targetObject, that.targetObject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(handler, targetObject);
  }

  public void invoke(Object object) {
    try {
      handler.invoke(targetObject, object);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
