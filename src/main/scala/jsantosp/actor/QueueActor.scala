package jsantosp.actor

import akka.actor.{Actor,ActorRef}
import jsantosp.{ Queue, Message, Topic }

trait QueueActor extends Actor with Queue {

  def receive = {

    case Subscribe(topic) =>
      val subscriber = sender
      subscribe(topic, subscriber ! _)

    case Publish(topic, message) =>
      publish(topic, message)

  }

}

case class Subscribe(topic: Topic) extends Command
case class Publish(topic: Topic, message: Message) extends Command

case class Subscribed(topic: Topic, subscriber: ActorRef) extends Event
case class Published(topic: Topic, message: Message)