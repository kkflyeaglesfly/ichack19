package cogni.cogni.model

data class Post(
        val id: Long,
        val userId: Long,
        val title: String,
        val body: String,
        val category: PostCategory,
        var removed: Boolean = false,
        var locked: Boolean = false,
        val upvotes: MutableList<User> = mutableListOf(),
        var followUps: MutableList<String> = mutableListOf(),
        var replies: MutableList<Reply> = mutableListOf(),
        val friends: MutableList<User> = mutableListOf(),
        val reports: MutableList<User> = mutableListOf()
)
