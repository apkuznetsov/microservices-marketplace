package kuznetsov.marketplace.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope

@SpringBootApplication
@RefreshScope
class MarketplaceChatApplication

fun main(args: Array<String>) {
    runApplication<MarketplaceChatApplication>(*args)
}
