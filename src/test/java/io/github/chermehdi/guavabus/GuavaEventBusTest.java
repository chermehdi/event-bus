package io.github.chermehdi.guavabus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author chermehdi
 */
class GuavaEventBusTest {
  private EventBus bus;
  private EventBus anotherBus;
  private HandlerClass mockedHandler;

  @BeforeEach
  void initBus() {
    bus = new EventBus("GuavaEventBus");
    anotherBus = new EventBus("AnotherGuavaEventBus");

    mockedHandler = Mockito.mock(HandlerClass.class);
    bus.register(mockedHandler);
    anotherBus.register(mockedHandler);
  }

  @Test
  void postEventObjectSingleBus() {
    bus.post(new EventObject("some event"));
    bus.post(new EventObject("some event"));
    verify(mockedHandler, times(0)).handlStub(any());
    verify(mockedHandler, times(2)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
    verify(mockedHandler, times(0)).doesNothingWrongEvent(any());
    verify(mockedHandler, times(0)).doesNothingZeroParameter();
  }

  @Test
  void postStubObjectSingleBus() {
    bus.post(new StubObject("some stub"));
    bus.post(new StubObject("some stub"));
    verify(mockedHandler, times(2)).handlStub(any());
    verify(mockedHandler, times(0)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
    verify(mockedHandler, times(0)).doesNothingWrongEvent(any());
    verify(mockedHandler, times(0)).doesNothingZeroParameter();
  }

  @Test
  void postEventObjectMultiBus() {
    anotherBus.post(new EventObject("some event"));
    bus.post(new EventObject("some event"));

    verify(mockedHandler, times(0)).handlStub(any());
    verify(mockedHandler, times(2)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
    verify(mockedHandler, times(0)).doesNothingWrongEvent(any());
    verify(mockedHandler, times(0)).doesNothingZeroParameter();
  }

  @Test
  void postEventObjectMultiBusWrongArg() {
    anotherBus.post("A wrong event argument");
    bus.post("A wrong event argument");

    verify(mockedHandler, times(0)).handlStub(any());
    verify(mockedHandler, times(0)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
    verify(mockedHandler, times(0)).doesNothingWrongEvent(any());
    verify(mockedHandler, times(0)).doesNothingZeroParameter();
  }

  @Test
  void register() {
    assertTrue(bus.getInvocations().containsKey(StubObject.class));
    assertTrue(bus.getInvocations().containsKey(EventObject.class));
    assertTrue(anotherBus.getInvocations().containsKey(EventObject.class));
  }

  @Test
  void unregister() {
    bus.unregister(mockedHandler);
    anotherBus.unregister(mockedHandler);

    anotherBus.post(new EventObject("some event"));
    bus.post(new EventObject("some event"));

    anotherBus.post(new StubObject("some stub"));
    bus.post(new StubObject("some stub"));

    verify(mockedHandler, times(0)).handlStub(any());
    verify(mockedHandler, times(0)).handleEvent(any());
    verify(mockedHandler, times(0)).doesNothing(any());
    verify(mockedHandler, times(0)).doesNothingWrongEvent(any());
    verify(mockedHandler, times(0)).doesNothingZeroParameter();

    assertTrue(bus.getInvocations().isEmpty());
    assertTrue(anotherBus.getInvocations().isEmpty());
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

    @Subscribe("GuavaEventBus")
    public void handlStub(StubObject stub) {
      System.out.println("i got stub object " + stub);
    }

    @Subscribe("GuavaEventBus")
    @Subscribe("AnotherGuavaEventBus")
    public void handleEvent(EventObject event) {
      System.out.println("i got event object " + event);
    }

    public void doesNothing(EventObject object) {
      System.out.println("does nothing never gets called !");
    }

    @Subscribe("WrongGuavaEventBus")
    public void doesNothingWrongEvent(EventObject object) {
      System.out.println("does nothing never gets called because it subscribed to the wrong event !");
    }

    @Subscribe("GuavaEventBus")
    @Subscribe("AnotherGuavaEventBus")
    public void doesNothingZeroParameter() {
      System.out.println("this method will be ignored !");
    }
  }
}