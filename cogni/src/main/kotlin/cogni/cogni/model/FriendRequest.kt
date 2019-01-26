package cogni.cogni.model

data class FriendRequest(
        val fromUserId: Long,
        val toUserId: Long,
        val postId: Long
)