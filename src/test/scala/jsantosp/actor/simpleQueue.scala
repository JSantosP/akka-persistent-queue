package jsantosp.actor

import akka.actor.{ ActorRef, Actor }
import jsantosp.{ Topic, Message }

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
  topic: Topic,
  listener: ActorRef) extends Subscriber(queue, topic) {

  def handleMessage = {
    case msg => listener ! msg
  }

}

class SimplePublisher(
  queue: ActorRef,
  topic: Topic) extends Publisher(queue, topic)

case object SampleTopic extends Topic

case class SampleMessage(content: String) extends Message