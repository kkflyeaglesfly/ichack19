package cogni.cogni.model

data class Reply(
        val userId: Long,
        val body: String,
        val upvotes: Int,
        val reports: Int
)
