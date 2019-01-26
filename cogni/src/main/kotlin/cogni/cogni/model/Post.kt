package cogni.cogni.model

data class Post(
        val id: Long,
        val userId: Long,
        val upvotes: Int,
        val title: String,
        val body: String,
        val followUps: MutableList<String>?,
        var replies: MutableList<Reply>?,
        val friends: MutableList<User>?
)
