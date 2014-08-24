package jsantosp.actor

import akka.actor.{Actor,ActorRef}
import jsantosp.{ QueueLike, Message, Topic }

trait Queue extends Actor with QueueLike {

  def receive = {

    case Subscribe(topic) =>
      val subscriber = sender
      subscribe(topic, subscriber ! _)
      sender ! Subscribed(topic,sender)

    case Publish(topic, message) =>
      publish(topic, message)
      sender ! Published(topic,message)

  }

}

case class Subscribe(topic: Topic) extends Command
case class Publish(topic: Topic, message: Message) extends Command

case class Subscribed(topic: Topic, subscriber: ActorRef) extends Event
case class Published(topic: Topic, message: Message)