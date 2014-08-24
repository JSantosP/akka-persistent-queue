package jsantosp.actor

import akka.actor.{ ActorRef, Props }
import jsantosp.{ Topic, Message }

class QueueTest extends TestKitSpec("MyActorSystem") {

  "QueueActor" should {
    "should subscribe and publish" in {

      val queue = system.actorOf(
        Props(new SimpleQueue()), "queue")

      val subscriber = system.actorOf(
        Props(new SimpleSubscriber(queue, SampleTopic, systemRef)), "subscriber")

      val publisher = system.actorOf(
        Props(new SimplePublisher(queue, SampleTopic)), "publisher")

      publisher ! SampleMessage("hi")

      expectMsg(SampleMessage("hi"))

    }
  }

}