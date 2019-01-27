package cogni.cogni.model

data class Reply(
        val id: Long,
        val userId: Long,
        val name: String,
        val body: String,
        val upvotes: MutableList<User>,
        val downvotes: MutableList<User>,
        val reports: Int
)
