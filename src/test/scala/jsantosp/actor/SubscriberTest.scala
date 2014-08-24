package jsantosp.actor

import akka.actor.{ ActorRef, Props }
import jsantosp.{ Topic, Message }

class SubscriberTest extends TestKitSpec("MyActorSystem") {

  val mockedQueue = systemRef

  "Subscriber" should {

    "subscribe to an existing queue/topic" in {

      val subscriber = system.actorOf(
        Props(new SimpleSubscriber(mockedQueue, SampleTopic, systemRef)), "subscriber1")

      expectMsg(Subscribe(SampleTopic))

    }

    "not process any message until subscription is confirmed" in {

      val subscriber = system.actorOf(
        Props(new SimpleSubscriber(mockedQueue, SampleTopic, systemRef)), "subscriber2")

      expectMsg(Subscribe(SampleTopic))

      subscriber ! "some-irretrievable-message"

      expectNoMsg

      subscriber ! Subscribed(SampleTopic,subscriber)

      subscriber ! "some-irretrievable-message"

      expectMsg("some-irretrievable-message")

    }

  }

}