package kuznetsov.marketplace.chat.dto

import kuznetsov.marketplace.chat.domain.MessageType

data class ChatMessageDto(

    val type: MessageType = MessageType.CHAT,

    val content: String? = "",

    val sender: String

)
