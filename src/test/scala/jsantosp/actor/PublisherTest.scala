package jsantosp.actor

import akka.actor.{ ActorRef, Props }
import jsantosp.{ Topic, Message }

class PublisherTest extends TestKitSpec("MyActorSystem") {

  val mockedQueue = systemRef

  "PublisherActor" should {

    "publish to an existing queue/topic" in {

      val publisher = system.actorOf(
        Props(new SimplePublisher(mockedQueue, SampleTopic)), "publisher1")

      val someMessage = SampleMessage("some-message")

      publisher ! someMessage

      expectMsg(Publish(SampleTopic,someMessage))

      publisher ! Published(SampleTopic,someMessage)

    }

    "stash messages until publication is confirmed" in {

      val publisher = system.actorOf(
        Props(new SimplePublisher(mockedQueue, SampleTopic)), "publisher2")

      val someMessage = SampleMessage("some-message")
      val someStashedMessage = SampleMessage("some-stashed-message")

      publisher ! someMessage

      expectMsg(Publish(SampleTopic,someMessage))

      publisher ! someStashedMessage

      expectNoMsg

      publisher ! Published(SampleTopic,someMessage)

      expectMsg(Publish(SampleTopic,someStashedMessage))

    }

  }

}