package kuznetsov.marketplace.chat.repository

interface UserRepository {

    fun addUser(sessionId: String, username: String)

    fun getUserBySessionId(sessionId: String): String?

    fun getAllUsers(): Collection<String>

    fun containsSessionId(sessionId: String): Boolean

    fun containsUsername(username: String): Boolean

    fun removeUserBySessionId(sessionId: String): String?

}