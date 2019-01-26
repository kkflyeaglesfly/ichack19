package cogni.cogni.model

data class User(
        val id: Long,
        val email: String,
        val name: String,
        val password: String,
        val anonymity: Boolean,
        val friends: MutableList<User>,
        enum class userType {general, verified, admin}
)