package cogni.cogni.model

data class Reply(
        val id: Long,
        val userId: Long,
        val name: String,
        val body: String,
        val upvotes: Int,
        val reports: MutableList<User>
)
