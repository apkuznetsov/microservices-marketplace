package kuznetsov.marketplace.chat.service

import kuznetsov.marketplace.chat.dto.ChatMessageDto

interface ChatService {

    fun addUserToChat(sessionId: String, username: String?): ChatMessageDto

    fun removeUserFromChat(sessionId: String)

    fun broadcastMessage(sessionId: String, message: ChatMessageDto): ChatMessageDto

    fun executeMessageCommand(message: ChatMessageDto): ChatMessageDto

}