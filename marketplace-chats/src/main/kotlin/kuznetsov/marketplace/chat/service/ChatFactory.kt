package kuznetsov.marketplace.chat.service

import kuznetsov.marketplace.chat.dto.ChatMessageDto

interface ChatFactory {

    fun messageUserLoginAccepted(username: String): ChatMessageDto

    fun messageUserDeclined(reason: String?): ChatMessageDto

    fun messageUserJoined(username: String): ChatMessageDto

    fun messageUserLeft(username: String): ChatMessageDto

}