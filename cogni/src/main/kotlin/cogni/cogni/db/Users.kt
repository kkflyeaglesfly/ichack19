package cogni.cogni.db

import cogni.cogni.model.User
import cogni.cogni.model.UserType

object Users {
    var users: MutableList<User> = mutableListOf(
            User(0, "sam@cogni.com", "Sam Liem", "donald", UserType.ADMIN),
            User(1, "dhru@cogni.com", "Dhru Devalia", "trustno1", UserType.ADMIN)
    )

    fun getUserByEmailPassword(email: String, password: String) : User? {
        return users.find { user -> user.email == email && user.password == password }
    }

    fun getUserById(id: Long) : User? {
        return users.find { user -> user.id == id }
    }

    fun createUser(email: String, name: String, password: String, type: UserType = UserType.GENERAL) : User {
        val user = User(users.size.toLong(), email, name, password, type)
        users.add(user)
        return user
    }
}