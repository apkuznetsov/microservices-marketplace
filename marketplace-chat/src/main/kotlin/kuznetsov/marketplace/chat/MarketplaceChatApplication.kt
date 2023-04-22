package kuznetsov.marketplace.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketplaceChatApplication

fun main(args: Array<String>) {
	runApplication<MarketplaceChatApplication>(*args)
}
