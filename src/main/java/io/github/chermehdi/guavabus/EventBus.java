package io.github.chermehdi.guavabus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Simple implementation demonstrating how a guava EventBus works generally, without all the noise
 * of special cases handling, and special guava collections
 *
 * @author chermehdi
 */
public class EventBus {

  private Map<Class<?>, List<Invocation>> invocations;

  private String name;

  public EventBus(String name) {
    this.name = name;
    invocations = new ConcurrentHashMap<>();
  }

  public void post(Object object) {
    Class<?> clazz = object.getClass();
    if (invocations.containsKey(clazz)) {
      invocations.get(clazz).forEach(invocation -> invocation.invoke(object));
    }
  }

  public void register(Object object) {
    Class<?> currentClass = object.getClass();
    // we try to navigate the object tree back to object ot see if
    // there is any annotated @Subscribe classes
    while (currentClass != null) {
      List<Method> subscribeMethods = findSubscriptionMethods(currentClass);

      for (Method method : subscribeMethods) {
        // we know for sure that it has only one parameter
        Class<?> type = method.getParameterTypes()[0];
        if (invocations.containsKey(type)) {
          invocations.get(type).add(new Invocation(method, object));
        } else {
          List<Invocation> temp = new Vector<>();
          temp.add(new Invocation(method, object));
          invocations.put(type, temp);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
  }

  private List<Method> findSubscriptionMethods(Class<?> type) {
    List<Method> subscribeMethods = Arrays.stream(type.getDeclaredMethods())
        .filter(this::isSubscribed)
        .collect(Collectors.toList());
    checkSubscriberMethods(subscribeMethods);
    return subscribeMethods;
  }

  private void checkSubscriberMethods(List<Method> subscribeMethods) {
    boolean hasMoreThanOneParameter = subscribeMethods.stream()
        .anyMatch(method -> method.getParameterCount() != 1);
    if (hasMoreThanOneParameter) {
      throw new IllegalArgumentException(
          "Method annotated with @Susbscribe has more than one parameter");
    }
  }

  private boolean isSubscribed(Method method) {
    Subscribe[] subscribes = method.getAnnotationsByType(Subscribe.class);
    return Arrays.stream(subscribes).anyMatch(subscribe -> this.name.equals(subscribe.value()));
  }

  public Map<Class<?>, List<Invocation>> getInvocations() {
    return invocations;
  }

  public String getName() {
    return name;
  }
}
