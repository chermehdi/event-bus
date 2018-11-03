package io.github.chermehdi.guavabus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author chermehdi
 */
class GuavaEventBusTest {

  @Test
  void post() {
    EventBus bus = new EventBus("GuavaEventBus");
    HandlerClass mockedHandler = Mockito.mock(HandlerClass.class);
    bus.register(mockedHandler);
    bus.post(new EventObject("some event"));
    bus.post(new EventObject("some event"));
    verify(mockedHandler, times(2)).handleEvent(any());
    verify(mockedHandler, times(0)).handlStub(any());
    bus.post(new StubObject("some stub"));
    verify(mockedHandler, times(1)).handlStub(any());
    verify(mockedHandler, times(2)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
  }

  @Test
  void register() {
    EventBus bus = new EventBus("GuavaEventBus");
    HandlerClass mockedHandler = Mockito.mock(HandlerClass.class);
    bus.register(mockedHandler);
    assertTrue(bus.getInvocations().containsKey(StubObject.class));
  }

  class StubObject {

    String name;

    public StubObject(String name) {
      this.name = name;
    }

    public String toString() {
      return "StubObject " + name;
    }
  }

  class EventObject {

    String name;

    public EventObject(String name) {
      this.name = name;
    }

    public String toString() {
      return "EventObject " + name;
    }
  }

  class HandlerClass {

    @Subscribe
    public void handlStub(StubObject stub) {
      System.out.println("i got stub object " + stub);
    }

    @Subscribe
    public void handleEvent(EventObject event) {
      System.out.println("i got event object " + event);
    }

    public void doesNothing(EventObject object) {
      System.out.println("does nothing never gets called !");
    }
  }
}