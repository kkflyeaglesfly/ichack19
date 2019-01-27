package cogni.cogni.model

data class Reply(
        val id: Long,
        val userId: Long,
        val name: String,
        val body: String,
        var removed: Boolean = false,
        val reports: MutableList<User> = mutableListOf(),
        val upvotes: MutableList<User> = mutableListOf(),
        val downvotes: MutableList<User> = mutableListOf()
)
