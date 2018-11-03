package io.github.chermehdi.bus.impl;

import io.github.chermehdi.bus.Event;
import io.github.chermehdi.bus.EventBus;
import io.github.chermehdi.bus.Subscribable;
import java.util.List;
import java.util.Vector;

/**
 * @author chermehdi
 */
public class AsyncEventBus implements EventBus {

  private List<Subscribable> subscribers;

  public AsyncEventBus() {
    subscribers = new Vector<>();
  }

  @Override
  public void register(Subscribable subscribable) {
  }

  @Override
  public void dispatch(Event<?> event) {

  }

  @Override
  public List<Subscribable> getSubscribers() {
    return null;
  }
}
