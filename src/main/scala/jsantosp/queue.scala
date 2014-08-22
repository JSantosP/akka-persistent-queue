package jsantosp

import akka.actor.ActorRef

trait Queue {

	def subscribe(topic: Topic, callback: Message => Unit): Unit

	def publish(topic: Topic, message: Message): Unit

}

trait Topic

case object GeneralTopic extends Topic