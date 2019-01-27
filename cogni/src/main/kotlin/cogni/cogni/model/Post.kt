package cogni.cogni.model

data class Post(
        val id: Long,
        val userId: Long,
        val upvotes: MutableList<User> = mutableListOf(),
        val title: String,
        val body: String,
        var followUps: MutableList<String> = mutableListOf(),
        var replies: MutableList<Reply> = mutableListOf(),
        val friends: MutableList<User> = mutableListOf(),
        val reports: MutableList<User> = mutableListOf(),
        var removed: Boolean = false,
        var locked: Boolean = false
)
