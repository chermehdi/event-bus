package io.github.chermehdi.bus;

import java.util.List;

/**
 * Description of the contract of a generic EventBus implementation, the library contains two main
 * version, Sync and Async event bus implementations, if you want to provide your own implementation
 * and stay compliant with the components of the library just implement this contract
 *
 * @author chermehdi
 */
public interface EventBus {

  /**
   * registers a new subscribable to this EventBus instance
   */
  void register(Subscribable subscribable);

  /**
   * send the given event in this EventBus implementation to be consumed by interested subscribers
   */
  void dispatch(Event<?> event);

  /**
   * get the list of all the subscribers associated with this EventBus instance
   */
  List<Subscribable> getSubscribers();
}
