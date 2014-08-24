package jsantosp.actor

import akka.actor.{ ActorSystem, Actor, Props }
import akka.testkit.{ TestKit, ImplicitSender }
import org.scalatest.{ WordSpecLike, Matchers, BeforeAndAfterAll }

abstract class TestKitSpec(systemName: String) extends TestKit(ActorSystem(systemName))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  val systemRef = self

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

}