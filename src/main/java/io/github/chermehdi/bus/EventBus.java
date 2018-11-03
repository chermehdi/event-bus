package io.github.chermehdi.bus;

import java.util.List;

/**
 * @author chermehdi
 */
public interface EventBus {

  void register(Subscribable subscribable);

  void dispatch(Event<?> event);

  List<Subscribable> getSubscribers();
}
