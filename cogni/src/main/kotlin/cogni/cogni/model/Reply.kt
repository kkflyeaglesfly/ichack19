package cogni.cogni.model

data class Reply(
    val id: Long,
    val userId: Long,
    val body: String,
    val upvotes: Int
)
