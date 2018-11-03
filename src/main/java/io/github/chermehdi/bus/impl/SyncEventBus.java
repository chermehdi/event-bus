package io.github.chermehdi.bus.impl;

import io.github.chermehdi.bus.Event;
import io.github.chermehdi.bus.EventBus;
import io.github.chermehdi.bus.Subscribable;
import java.util.List;
import java.util.Vector;

/**
 * @author chermehdi
 */
public class SyncEventBus implements EventBus {

  private List<Subscribable> subscribers;

  public SyncEventBus() {
    subscribers = new Vector<>();
  }

  @Override
  public void register(Subscribable subscribable) {
    subscribers.add(subscribable);
  }

  @Override
  public void dispatch(final Event<?> event) {
    subscribers
      .stream()
      .filter(subscriber -> subscriber.supports().contains(event.getData().getClass()))
      .forEach(subscriber -> subscriber.handleChange(event));
  }

  @Override
  public List<Subscribable> getSubscribers() {
    return subscribers;
  }
}
