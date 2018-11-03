package io.github.chermehdi.bus.impl;

import io.github.chermehdi.bus.Event;
import io.github.chermehdi.bus.EventBus;
import io.github.chermehdi.bus.Subscribable;
import java.util.List;

/**
 * @author chermehdi
 */
public class AsyncEventBus implements EventBus {

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
