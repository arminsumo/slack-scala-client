package slack

import akka.actor._
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
    rtmClient.onEvent(println)
  }
}

//          Await.result(apiClient.getChannelHistory(e.channel, e.thread_ts, None, Some(1), Some(1)), Duration.Inf).messages.value.map(jsValue => jsValue.validate[ThreadMessage].get).head.text
