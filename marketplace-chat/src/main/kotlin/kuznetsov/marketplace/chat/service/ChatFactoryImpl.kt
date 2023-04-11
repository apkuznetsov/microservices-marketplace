package kuznetsov.marketplace.chat.service

import kuznetsov.marketplace.chat.domain.MessageType
import kuznetsov.marketplace.chat.dto.ChatMessageDto
import org.springframework.stereotype.Service

@Service
class ChatFactoryImpl : ChatFactory {

    override fun messageUserLoginAccepted(username: String): ChatMessageDto {
        return ChatMessageDto(
            type = MessageType.LOGIN_ACCEPTED,
            content = "You have successfully logged as $username",
            sender = ChatProperties.SENDER_SERVER
        )
    }

    override fun messageUserDeclined(reason: String?): ChatMessageDto {
        return ChatMessageDto(
            type = MessageType.LOGIN_DECLINED,
            content = reason,
            sender = ChatProperties.SENDER_SERVER
        )
    }

    override fun messageUserJoined(username: String): ChatMessageDto {
        return ChatMessageDto(
            type = MessageType.JOIN,
            content = "$username joined the chat",
            sender = ChatProperties.SENDER_SERVER
        )
    }

    override fun messageUserLeft(username: String): ChatMessageDto {
        return ChatMessageDto(
            type = MessageType.LEFT,
            content = "$username left the chat",
            sender = ChatProperties.SENDER_SERVER
        )
    }

}
