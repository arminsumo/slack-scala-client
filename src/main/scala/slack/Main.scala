package slack

import akka.actor._
import slack.api.SlackApiClient
import slack.rtm.SlackRtmClient

object SlackBot {
  implicit val system: ActorSystem = ActorSystem("slack")
  val token = sys.env("SLACK_BOT_TOKEN")
  val apiClient = SlackApiClient(token)
  val rtmClient = SlackRtmClient(token)

  def main(args: Array[String]): Unit = {
    rtmClient.onEvent(println)
  }
}
