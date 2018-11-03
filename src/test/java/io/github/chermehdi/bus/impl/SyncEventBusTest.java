package io.github.chermehdi.bus.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.chermehdi.bus.Event;
import io.github.chermehdi.bus.EventBus;
import io.github.chermehdi.bus.EventBusFactory;
import io.github.chermehdi.bus.Subscribable;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author chermehdi
 */
class SyncEventBusTest {

  @Test
  void register() {
  }

  @Test
  void dispatch() {
    EventBus eventBus = EventBusFactory.createSyncEventBus();
    Subscribable mock = Mockito.mock(Subscribable.class);

    when(mock.supports()).thenReturn(new HashSet<>(Collections.singletonList(EventStub.class)));

    eventBus.register(mock);
    eventBus.dispatch(new EventStub());
    eventBus.dispatch(new EventStub());
    eventBus.dispatch(new EventStub());

    verify(mock, times(3)).handle(any());
  }

  @Test
  void getSubscribers() {
  }

  class EventStub implements Event<String> {

    @Override
    public String getData() {
      return "hello special Event";
    }
  }
}
