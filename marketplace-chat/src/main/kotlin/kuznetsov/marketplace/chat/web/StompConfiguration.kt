package kuznetsov.marketplace.chat.web

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class StompConfiguration : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(endpointRegistry: StompEndpointRegistry) {
        endpointRegistry.addEndpoint("/ws").withSockJS()
        endpointRegistry.addEndpoint("/ws")
    }

    override fun configureMessageBroker(brokerRegistry: MessageBrokerRegistry) {
        brokerRegistry.setApplicationDestinationPrefixes("/app")
        brokerRegistry.enableSimpleBroker("/topic", "/queue")
    }

}
