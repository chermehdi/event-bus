package io.github.chermehdi.bus;

import io.github.chermehdi.bus.impl.AsyncEventBus;
import io.github.chermehdi.bus.impl.SyncEventBus;

/**
 * @author chermehdi
 */
public final class EventBusFactory {

  /**
   * the factory does not assume the EventBus to be a singleton, it has another method specific to
   * that each time it is called, an new instance of EventBus is returned
   */
  public static synchronized EventBus createSyncEventBus() {
    return new SyncEventBus();
  }

  /**
   * creates and returns an SyncEventBus, the instance is held inside an enumeration, to make it a
   * singleton inside the vm
   */
  public static synchronized EventBus createSingletonSyncEventBus() {
    return EventBusHolder.SYNC_INSTANCE.bus;
  }

  /**
   * the factory does not assume the EventBus to be a singleton, it has another method specific to
   * that each time it is called, an new instance of EventBus is returned
   */
  public static synchronized EventBus createAsyncEventBus() {
    return new AsyncEventBus();
  }

  /**
   * creates and returns an AsyncEventBus, the instance is held inside an enumeration, to make it a
   * singleton inside the vm
   */
  public static synchronized EventBus createSingletonAsyncEventBus() {
    return EventBusHolder.ASYNC_INSTANCE.bus;
  }

  private enum EventBusHolder {

    SYNC_INSTANCE(new SyncEventBus()),


    ASYNC_INSTANCE(new AsyncEventBus());

    private EventBus bus;

    EventBusHolder(EventBus bus) {
      this.bus = bus;
    }
  }
}
