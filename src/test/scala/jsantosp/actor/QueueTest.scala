package jsantosp.actor

import akka.actor.{ ActorRef, Props }
import jsantosp.{ Topic, Message }

class QueueTest extends TestKitSpec("MyActorSystem") {

  class SimpleQueue extends Queue {

    var callbacks: Map[Topic, Set[Callback]] = Map()

    def subscribe(topic: Topic, callback: Callback) {
      callbacks += (topic -> (callbacks.getOrElse(topic, Set()) + callback))
    }

    def publish(topic: Topic, message: Message) {
      for {
        callbackSet <- callbacks.get(topic)
        callback <- callbackSet
      } callback(message)
    }

  }

  class SimpleSubscriber(
    queue: ActorRef,
    topic: Topic) extends Subscriber(queue, topic) {

    def handleMessage = {
      case msg => systemRef ! msg
    }

  }

  class SimplePublisher(
    queue: ActorRef,
    topic: Topic) extends Publisher(queue, topic)

  case object SampleTopic extends Topic

  case class SampleMessage(content: String) extends Message

  "QueueActor" should {
    "should subscribe and publish" in {

      val queue = system.actorOf(
        Props(new SimpleQueue()), "queue")

      val subscriber = system.actorOf(
        Props(new SimpleSubscriber(queue, SampleTopic)), "subscriber")

      val publisher = system.actorOf(
        Props(new SimplePublisher(queue, SampleTopic)), "publisher")

      publisher ! SampleMessage("hi")

      expectMsg(SampleMessage("hi"))

    }
  }

}