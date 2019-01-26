package cogni.cogni.db

import cogni.cogni.model.User

object Users {
    var users: List<User> = listOf(User(0, "sam@cogni.com", "Sam Liem", "donald"))

    fun getUserByEmailPassword(email: String, password: String) : User? {
        return users.find { user -> user.email.equals(email) && user.password.equals(password) }
    }

    fun getUserById(id: Long) : User? {
        return users.find { user -> user.id == id }
    }

    fun createUser(email: String, name: String, password: String) : User {
        val user: User = User(users.size.toLong(), email, name, password)
        return user
    }
}