package cogni.cogni.http

data class FollowupReq (
        val postId: Long,
        val userId: Long,
        val body: String
)