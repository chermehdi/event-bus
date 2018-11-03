package io.github.chermehdi.bus;

import java.util.Set;

/**
 * @author chermehdi
 */
public interface Subscribable {

  void handleChange(Event<?> event);

  Set<Class<?>> supports();
}
