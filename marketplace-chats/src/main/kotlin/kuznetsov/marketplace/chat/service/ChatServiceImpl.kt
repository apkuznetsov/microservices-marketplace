package kuznetsov.marketplace.chat.service

import kuznetsov.marketplace.chat.domain.MessageType
import kuznetsov.marketplace.chat.dto.ChatMessageDto
import kuznetsov.marketplace.chat.repository.UserRepository
import kuznetsov.marketplace.chat.service.ChatProperties.COMMAND_USERS
import kuznetsov.marketplace.chat.service.exception.UserNotAuthorizedException
import kuznetsov.marketplace.chat.service.exception.UsernameInvalidException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl : ChatService {

    private val log = LoggerFactory.getLogger(ChatServiceImpl::class.java)

    @Autowired
    private lateinit var chatValidator: ChatValidator

    @Autowired
    private lateinit var chatFactory: ChatFactory

    @Autowired
    private lateinit var userRepo: UserRepository

    @Autowired
    private lateinit var simpSender: SimpMessageSendingOperations

    override fun addUserToChat(sessionId: String, username: String?): ChatMessageDto {
        return try {
            chatValidator.validateUsernameOrThrow(username)

            userRepo.addUser(sessionId, username!!)

            simpSender.convertAndSend(
                ChatProperties.DESTINATION_TOPIC_PUBLIC,
                chatFactory.messageUserJoined(username)
            )

            log.info("User $username joined the chat")
            chatFactory.messageUserLoginAccepted(username)

        } catch (e: UsernameInvalidException) {
            log.info("Unsuitable name $username")
            chatFactory.messageUserDeclined(e.message)
        }
    }

    override fun removeUserFromChat(sessionId: String) {
        userRepo.removeUserBySessionId(sessionId)?.let {
            simpSender.convertAndSend(
                ChatProperties.DESTINATION_TOPIC_PUBLIC,
                chatFactory.messageUserLeft(it)
            )
        }
    }

    override fun broadcastMessage(sessionId: String, message: ChatMessageDto): ChatMessageDto {
        val username = userRepo.getUserBySessionId(sessionId)
            ?: throw UserNotAuthorizedException("User $sessionId is not authorized")

        return ChatMessageDto(
            type = MessageType.CHAT,
            content = message.content,
            sender = username
        )
    }

    override fun executeMessageCommand(message: ChatMessageDto): ChatMessageDto {
        return when (message.content) {
            COMMAND_USERS -> ChatMessageDto(
                type = MessageType.COMMAND_RESULT,
                content = userRepo.getAllUsers().toString(),
                sender = ChatProperties.SENDER_SERVER
            )

            else -> ChatMessageDto(
                type = MessageType.COMMAND_ERROR,
                content = "${message.content} is not a valid command.",
                sender = ChatProperties.SENDER_SERVER
            )
        }
    }

}
