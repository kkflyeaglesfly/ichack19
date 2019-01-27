package cogni.cogni.http

import cogni.cogni.model.Post

data class FriendReq(
        val fromUserId: Long,
        val toUserId: Long,
        val postId : Long
)
