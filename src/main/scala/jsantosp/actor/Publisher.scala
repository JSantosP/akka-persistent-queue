package jsantosp.actor

import akka.actor.{ Actor, ActorRef, Stash }
import jsantosp.{ Topic, Message }

class Publisher(queue: ActorRef, topic: Topic) extends Actor
  with Stash {

  import context._

  def receive = working

  def working: Receive = {
    case msg: Message =>
      queue ! Publish(topic, msg)
      become(awaitForConfirmation(msg))
  }

  def awaitForConfirmation(msg: Message): Receive = {
    case Published(someTopic, someMessage) if someTopic == topic && someMessage == msg =>
      unstashAll
      become(working)
    case otherMessage =>
      stash
  }

}
