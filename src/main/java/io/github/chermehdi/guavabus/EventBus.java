package io.github.chermehdi.guavabus;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Simple implementation demonstrating generally how guava-like EventBus works, without all the
 * additional code of special cases handling and the usage of special guava collections
 *
 * @author chermehdi
 */
public class EventBus {

  private Map<Class<?>, Set<Invocation>> invocations;

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

    /**
     * try to navigate the object tree back to {@link Object} class while
     * checking if there is any @{@link Subscribe} annotated methods
     */
    while (currentClass != null) {
      List<Method> subscribeMethods = findSubscriptionMethods(currentClass);

      for (Method method : subscribeMethods) {
        // we know for sure that it has only one parameter
        Class<?> type = method.getParameterTypes()[0];
        if (invocations.containsKey(type)) {
          invocations.get(type).add(new Invocation(method, object));
        } else {
          Set<Invocation> temp = new HashSet<>();
          temp.add(new Invocation(method, object));
          invocations.put(type, temp);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
  }

  public void unregister(Object object) {
    Class<?> currentClass = object.getClass();
    while (currentClass != null) {
      List<Method> subscribeMethods = findSubscriptionMethods(currentClass);

      for (Method method : subscribeMethods) {
        Class<?> type = method.getParameterTypes()[0];
        if (invocations.containsKey(type)) {
          Set<Invocation> invocationsSet = invocations.get(type);
          invocationsSet.remove(new Invocation(method, object));

          if(invocationsSet.isEmpty()) {
            invocations.remove(type);
          }
        }
      }
      currentClass = currentClass.getSuperclass();
    }
  }

  private List<Method> findSubscriptionMethods(Class<?> type) {
    List<Method> subscribeMethods = Arrays.stream(type.getDeclaredMethods())
        .filter(this::isSubscribed)
        .collect(Collectors.toList());
    return filterSingleParameterMethods(subscribeMethods);
  }

  private List<Method> filterSingleParameterMethods(List<Method> subscribeMethods) {
    return subscribeMethods.stream().filter(method -> method.getParameterCount() == 1).collect(Collectors.toList());
  }

  private boolean isSubscribed(Method method) {
    Subscribe[] subscribes = method.getAnnotationsByType(Subscribe.class);
    return Arrays.stream(subscribes).anyMatch(subscribe -> this.name.equals(subscribe.value()));
  }

  public Map<Class<?>, Set<Invocation>> getInvocations() {
    return invocations;
  }

  public String getName() {
    return name;
  }
}
