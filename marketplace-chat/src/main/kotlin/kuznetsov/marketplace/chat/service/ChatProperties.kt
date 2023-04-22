package kuznetsov.marketplace.chat.service

object ChatProperties {

    const val DESTINATION_CHAT_ENTER = "/chat.enter";
    const val DESTINATION_CHAT_SEND = "/chat.send";
    const val DESTINATION_CHAT_COMMAND = "/chat.command";

    const val DESTINATION_TOPIC_PUBLIC = "/topic/public"
    const val DESTINATION_QUEUE_USER = "/queue/reply"

    const val SENDER_SERVER = "CHAT_SERVER"

    const val COMMAND_USERS = "/users"

}
