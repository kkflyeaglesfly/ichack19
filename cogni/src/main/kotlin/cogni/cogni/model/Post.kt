package cogni.cogni.model

data class Post(
        val id: Long,
        val userId: Long,
        val upvotes: Int,
        val reports: Int,
        val title: String,
        val body: String,
        var followUps: MutableList<String>,
        var replies: MutableList<Reply>,
        val friends: MutableList<User>
)
