package cogni.cogni.model

data class Post(
        val id: Long,
        val userId: Long,
        val upvotes: MutableList<User>,
        val title: String,
        val body: String,
        var followUps: MutableList<String>,
        var replies: MutableList<Reply>,
        val friends: MutableList<User>,
        val reports: MutableList<User>,
        val category: PostCategory
)
