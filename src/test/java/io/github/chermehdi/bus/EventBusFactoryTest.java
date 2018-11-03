package io.github.chermehdi.bus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author chermehdi
 */
class EventBusFactoryTest {

  @Test
  void createdEventBusShouldBeTheSame() {
    EventBus eventBus1 = EventBusFactory.createSingletonSyncEventBus();
    EventBus eventBus2 = EventBusFactory.createSingletonSyncEventBus();
    assertSame(eventBus1, eventBus2);
  }

  @Test
  void createdEventBusShouldNotBeTheSame() {
    EventBus eventBus1 = EventBusFactory.createSyncEventBus();
    EventBus eventBus2 = EventBusFactory.createSyncEventBus();
    assertNotSame(eventBus1, eventBus2);
  }
  @Test
  void createdAsyncEventBusShouldBeTheSame() {
    EventBus eventBus1 = EventBusFactory.createSingletonAsyncEventBus();
    EventBus eventBus2 = EventBusFactory.createSingletonAsyncEventBus();
    assertSame(eventBus1, eventBus2);
  }

  @Test
  void createdAsyncEventBusShouldNotBeTheSame() {
    EventBus eventBus1 = EventBusFactory.createAsyncEventBus();
    EventBus eventBus2 = EventBusFactory.createAsyncEventBus();
    assertNotSame(eventBus1, eventBus2);
  }
}
