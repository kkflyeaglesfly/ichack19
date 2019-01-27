package cogni.cogni.http

data class FriendReq(
        val fromUserId: Long,
        val toUserId: Long,
        val postId : Long
)
