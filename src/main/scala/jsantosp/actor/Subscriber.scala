package jsantosp.actor

import akka.actor.{Actor,ActorRef}
import jsantosp.{Topic,Message}

abstract class Subscriber(queue: ActorRef,topic: Topic) extends Actor {

	import context._

	override def preStart {
		super.preStart
		queue ! Subscribe(topic)
	}

	def idle: Receive = {
		case Subscribed(_,_) => 
			become(handleMessage)
	}

	def handleMessage: Receive

	def receive = idle

}
