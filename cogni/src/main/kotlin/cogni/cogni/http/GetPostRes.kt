package cogni.cogni.http

import cogni.cogni.model.Reply
import cogni.cogni.model.User

data class GetPostRes (
        val postId: Long,
        val userId: Long,
        val name: String,
        val upvotes: List<User>,
        val title: String,
        val body: String,
        var followUps: List<String>?,
        var replies: List<Reply>?,
        var friends: List<User>?
)
