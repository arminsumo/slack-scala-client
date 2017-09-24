package slack

import java.net.URLEncoder
import java.nio.charset.Charset

import akka.actor._
import akka.http.scaladsl.model.Uri
import play.api.libs.json.{Format, JsValue, Json}
import slack.api.{BlockingSlackApiClient, SlackApiClient}
import slack.rtm.SlackRtmClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

object SlackBot {
  implicit val system: ActorSystem = ActorSystem("slack")
  val token = sys.env("SLACK_BOT_TOKEN")
  val apiClient = SlackApiClient(token)
  val rtmClient = SlackRtmClient(token)

  private def extract[T](jsFuture: Future[JsValue], field: String)(implicit system: ActorSystem, fmt: Format[T]): Future[T] = {
    jsFuture.map(js => (js \ field).as[T])(system.dispatcher)
  }

  def main(args: Array[String]): Unit = {
//    println(URLEncoder.encode("hello?", "UTF-8"))
  //  println()
    //rtmClient.onEvent(println)
    //apiClient.postChatMessage("general", "hello?")
//
    for (i <- 1 to 100) {
      apiClient.postChatMessage("general", "hello?")
    }
  }
}

//          Await.result(apiClient.getChannelHistory(e.channel, e.thread_ts, None, Some(1), Some(1)), Duration.Inf).messages.value.map(jsValue => jsValue.validate[ThreadMessage].get).head.text
