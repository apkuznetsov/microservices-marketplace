package kuznetsov.marketplace.chat.web

import kuznetsov.marketplace.chat.domain.MessageType
import kuznetsov.marketplace.chat.dto.ChatMessageDto
import kuznetsov.marketplace.chat.service.ChatProperties
import kuznetsov.marketplace.chat.service.ChatProperties.DESTINATION_CHAT_COMMAND
import kuznetsov.marketplace.chat.service.ChatProperties.DESTINATION_CHAT_ENTER
import kuznetsov.marketplace.chat.service.ChatProperties.DESTINATION_CHAT_SEND
import kuznetsov.marketplace.chat.service.ChatService
import kuznetsov.marketplace.chat.service.exception.UserNotAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.*
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller


@Controller
class ChatStompController {

    private val log = LoggerFactory.getLogger(ChatStompController::class.java)

    @Autowired
    private lateinit var chatService: ChatService

    @MessageMapping(DESTINATION_CHAT_ENTER)
    @SendToUser(ChatProperties.DESTINATION_QUEUE_USER)
    fun chatEnter(
        header: SimpMessageHeaderAccessor,
        @Header("simpSessionId") sessionId: String,
        @Payload message: ChatMessageDto
    ): ChatMessageDto {

        log.info("New entering message: $message")

        val resultMessage = chatService.addUserToChat(sessionId, message.content)
        if (resultMessage.type == MessageType.LOGIN_ACCEPTED) {
            header.sessionAttributes?.set("username", message.content)
            header.sessionId = sessionId
        }

        return resultMessage
    }

    @MessageMapping(DESTINATION_CHAT_SEND)
    @SendTo(ChatProperties.DESTINATION_TOPIC_PUBLIC)
    fun chatBroadcast(
        @Payload message: ChatMessageDto,
        @Header("simpSessionId") sessionId: String
    ): ChatMessageDto {

        log.info("User ${message.sender} sends '${message.content}'")
        return chatService.broadcastMessage(sessionId, message)
    }

    @MessageMapping(DESTINATION_CHAT_COMMAND)
    @SendToUser(ChatProperties.DESTINATION_QUEUE_USER)
    fun executeCommand(
        @Payload message: ChatMessageDto,
        @Header("simpSessionId") sessionId: String
    ): ChatMessageDto {

        log.info("Incoming command $message from $sessionId")
        return chatService.executeMessageCommand(message)
    }

    @MessageExceptionHandler
    @SendToUser(ChatProperties.DESTINATION_QUEUE_USER)
    fun handleException(
        @Header("simpSessionId") sessionId: String,
        e: UserNotAuthorizedException
    ): ChatMessageDto {

        log.warn("Sending error ${e.message} for user $sessionId")
        return ChatMessageDto(
            type = MessageType.LOGIN_REQUIRED,
            content = "You are not authorized",
            sender = ChatProperties.SENDER_SERVER
        )
    }

}
