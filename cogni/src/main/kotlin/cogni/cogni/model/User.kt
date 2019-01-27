package cogni.cogni.model

data class User(
        val id: Long,
        val email: String,
        val name: String,
        val password: String,
        val userType: UserType,
        val friendRequests : MutableList<FriendRequest>,
        var karma: Int
)