# Event Bus

### Why i wrote it

- I write a [blog](https://javameweb.wordpress.com) from time to time and i needed code to help show the ideas i described in the blog post
- To show that it's not hard to write your own implementation of some idea (this is for my audience of students)

### How to run

- This is a maven project so you can compile and run using maven, just install the dependencies and you are good to go
- Make sure you have a JDK 1.8 or higher and an IDE is nice to have
- Take a look at the tests to see how the two implementations work generally and play with the code however you like, you can 
read the blog post for additional explanation .

### Example

##### Using Common EventBus implementation

```java
EventBus eventBus = EventBusFactory.createSingletonSyncEventBus();
Subscribable subs = new EventSubscribable(); // your own implementation
eventBus.register(subs);

// later on on the code ... 
eventBus.dispatch(new EventStub());
```  
##### Using The idea of Guave's EventBus

```java
EventBus eventBus = new EventBus("myEventBus")

eventBus.register(new Handler());

class Event {
  // your event
}
class Handler {
  
  @Subscribe
  public void handleEvent(Event event) {
    // consume the event as you like
  }
}

// later on in the code
eventBus.post(new Event());
```

### Contribution

- Bug fixes, improvements are all welcomed 😁