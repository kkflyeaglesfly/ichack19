package cogni.cogni.http

data class ReplyReq (
        val postId: Long,
        val userId: Long,
        val body: String,
        var upvotes: Int
)