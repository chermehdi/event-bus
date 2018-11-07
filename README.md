# Event Bus

### Why?

- I write a [blog post](https://javameweb.wordpress.com) from time to time and this repository is POC to the ideas explained in the blog post.
- To demonstrate to my students that it's not hard at all to implement such types of ideas.

### Running the project

- It's a maven based project, hence you can just compile and run using maven itself (Just don't forget to install the dependencies and you'll be good to go).
- Make sure you have at least a JDK 1.8 or higher installed in your machine and an IDE would be nice to have.
- Take a look at the unit tests to see how to use both implementations if the EventBus. Also play with code a little, you can read the blog post for additional explanations.

### Usage example

##### Using Common EventBus implementation

```java
EventBus eventBus = EventBusFactory.createSingletonSyncEventBus();
Subscribable subs = new EventSubscribable(); // your own implementation of the Subscribable interface
eventBus.register(subs);

// later on on the code ... 
eventBus.dispatch(new EventStub());
```
##### Using The idea of Guava's EventBus

```java
EventBus eventBus = new EventBus("myEventBusName")
Handler handler = new Handler();

eventBus.register(handler);

class Event {
  // your event's state/behaviours
}
class Handler {

  @Subscribe("myEventBusName")
  @Subscribe("multiEventSubscribingIsAlsoSupported")
  public void handleEvent(Event event) {
    // consume the event here
  }
}

// later on in the code
eventBus.post(new Event());

// can also unregister handlers later on
eventBus.register(handler);
```

### Contribution

- Bug fixes, improvements, and more features are all welcomed 😁 (Just make sure to fully describe what you've done and write some unit tests if necessary)
