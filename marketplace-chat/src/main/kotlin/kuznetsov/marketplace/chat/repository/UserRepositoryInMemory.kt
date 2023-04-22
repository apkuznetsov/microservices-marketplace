package kuznetsov.marketplace.chat.repository

import kuznetsov.marketplace.chat.service.exception.UsernameInvalidException
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class UserRepositoryInMemory : UserRepository {

    private val sessIdToUsername = ConcurrentHashMap<String, String>()
    private val takenUsernames = Collections.newSetFromMap<String>(ConcurrentHashMap())

    override fun addUser(sessionId: String, username: String) {
        if (takenUsernames.contains(username)) {
            throw UsernameInvalidException(
                "The name $username is already taken"
            )
        }

        sessIdToUsername[sessionId] = username
        takenUsernames.add(username)
    }

    override fun getUserBySessionId(sessionId: String): String? {
        return sessIdToUsername[sessionId]
    }

    override fun getAllUsers(): Collection<String> {
        return sessIdToUsername.values
    }

    override fun containsSessionId(sessionId: String): Boolean {
        return sessIdToUsername.containsKey(sessionId)
    }

    override fun containsUsername(username: String): Boolean {
        return takenUsernames.contains(username)
    }

    override fun removeUserBySessionId(sessionId: String): String? {
        val removedUsername = sessIdToUsername.remove(sessionId)
        takenUsernames.remove(removedUsername)
        return removedUsername
    }

}
