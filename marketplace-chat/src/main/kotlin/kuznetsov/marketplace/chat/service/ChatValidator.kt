package kuznetsov.marketplace.chat.service

interface ChatValidator {

    fun validateUsernameOrThrow(username: String?)

}