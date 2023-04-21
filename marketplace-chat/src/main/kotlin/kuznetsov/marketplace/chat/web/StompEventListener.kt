package kuznetsov.marketplace.chat.web

import kuznetsov.marketplace.chat.service.ChatServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent


@Component
class StompEventListener {
    private val logger = LoggerFactory.getLogger(StompEventListener::class.java)

    @Autowired
    private lateinit var chatServiceImpl: ChatServiceImpl

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent) {
        val sessionId = StompHeaderAccessor.wrap(event.message).sessionId
        logger.info("Established a new web socket connection $sessionId")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val header = StompHeaderAccessor.wrap(event.message)
        logger.info("Disconnected ${header.sessionId}")

        val username = header.sessionAttributes?.get("username") as String?
        if (username != null) {
            chatServiceImpl.removeUserFromChat(header.sessionId!!)
        }
    }

}