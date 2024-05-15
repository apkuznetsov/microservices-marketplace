package kuznetsov.marketplace.chat.service

import kuznetsov.marketplace.chat.service.exception.UsernameInvalidException
import org.springframework.stereotype.Service

@Service
class ChatValidatorImpl : ChatValidator {

    override fun validateUsernameOrThrow(username: String?) {
        validateUsernameIsNotNullOrNotEmpty(username)
    }

    private fun validateUsernameIsNotNullOrNotEmpty(username: String?) {
        if (username.isNullOrBlank()) {
            throw UsernameInvalidException("Username must not be null or empty")
        }
    }

}
