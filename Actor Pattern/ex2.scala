mport akka.Done
import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorContext, ActorSystem, Props}
import akka.pattern.ask
import akka.routing.RoundRobinPool
import akka.util.Timeout
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.io.Source
import scala.language.postfixOps

class Counter extends Actor {
  override def receive: Receive = TimingReceive {
    case file: String =>

      val content = Source.fromFile(file)
        .getLines().toList
        .map(row => row.split(",").toList)
        .size
      println("Number of words in file " + file + "–" + content)
      sender() ! Done
    case _ => None
  }
}

class TimingReceive(r: Receive, totalTime: Long)(implicit ctx: ActorContext) extends Receive {
  def isDefinedAt(o: Any): Boolean = {
    r.isDefinedAt(o)
  }

  def apply(o: Any): Unit = {
    val startTime = System.nanoTime()
    r(o)
    val newTotal = totalTime + (System.nanoTime() – startTime)
    println("Total time so far: " + totalTime + " milliseconds")
    ctx.become(new TimingReceive(r, newTotal))
  }
}

object WordCountUsingAkka {

  implicit val timeout: Timeout = Timeout(50 seconds)

  def main(args: Array[String]) {
    val system = ActorSystem("Counting")
    val props = Props[Counter].withRouter(RoundRobinPool(2))
    val actor = system.actorOf(props, "Ping")
   
    val result: List[Future[Done]] = List.fill[String](10)("/home/workspace/Desktop/enwik8").map { file =>
      (actor ? file).mapTo[Done]
    }
  }
}

object TimingReceive {
  def apply(r: Receive)(implicit ctx: ActorContext): Receive = new TimingReceive(r, 0)
}
