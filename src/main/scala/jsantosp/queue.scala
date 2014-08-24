package jsantosp

import akka.actor.ActorRef

trait QueueLike {

  type Callback = Message => Unit

  def subscribe(topic: Topic, callback: Callback): Unit

  def publish(topic: Topic, message: Message): Unit

}

trait Topic

case object GeneralTopic extends Topic