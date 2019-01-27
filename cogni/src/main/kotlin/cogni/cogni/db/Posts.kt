package cogni.cogni.db

import cogni.cogni.model.Post
import cogni.cogni.model.Reply
import cogni.cogni.model.User

object Posts {
    //var posts: MutableList<Post> = mutableListOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", mutableListOf(), mutableListOf(), mutableListOf(Users.users.get(1))))
    var posts: MutableList<Post> = mutableListOf(Post(0, 0, 0, mutableListOf(), "Welcome to Cogni!", "thanks i'm cured", mutableListOf(), mutableListOf(), mutableListOf()))
    
    fun getPostById(id: Long) : Post? {
        return posts.find { post : Post -> post.id == id}
    }

    fun reply(postId: Long, userId: Long, body: String): Int {
        val post: Post? = getPostById(postId)
        if (post != null) {
            post.replies.add(Reply(post.replies.size.toLong(), userId, "anon", body, mutableListOf(), mutableListOf(), 0))
            return 0
        }
        return -1
    }

    fun followup(userId: Long, id: Long, followup: String): Int {
        val post: Post? = getPostById(id)
        if (post != null && post.userId == userId) {
            post.followUps.add(followup)
            return 0
        }
        return -1
    }

    fun createPost(newPost: Post): Int {
        if (Users.isValidUserId(newPost.userId)) {
            posts.add(newPost)
            return 0
        }
        return -1
    }

    fun getUniqueId(): Long {
        return Users.users.size.toLong()
    }

    fun containsReplyFrom(postId: Long, userId: Long): Boolean {
        val post = getPostById(postId)
        if (post != null) {
            return post.replies.any { reply -> reply.userId == userId }
        }
        return false
    }


    fun removePost(postId : Long){
        posts.remove(getPostById(postId))
    }

    fun upvotePost(postId: Long, userId : Long){
        val post : Post = getPostById(postId)!!
        if (post.upvotes.contains(Users.getUserById(userId))){
            post.upvotes.remove(Users.getUserById(userId))
        } else {
            post.upvotes.add(Users.getUserById(userId)!!)
        }

        //change karma to reflect decision
    }

    fun upvoteReply(replyId: Long, userId: Long){
        var reply : Reply = getReplyFromReplyId(replyId)

        if (reply.downvotes.contains(Users.getUserById(userId))){
            reply.downvotes.remove(Users.getUserById(userId))
            reply.upvotes.add(Users.getUserById(userId)!!)
            return
        }
        if (reply.upvotes.contains(Users.getUserById(userId))){
            reply.upvotes.remove(Users.getUserById(userId))
        } else {
            reply.upvotes.add(Users.getUserById(userId)!!)
        }
        //change karma to reflect decision

    }

    private fun getReplyFromReplyId(replyId: Long): Reply {
        return posts[0].replies[0]
    }

    fun downVoteReply(replyId: Long, userId: Long){
        var reply : Reply = getReplyFromReplyId(replyId)

        if(reply.upvotes.contains(Users.getUserById(userId))){
            reply.upvotes.remove(Users.getUserById(userId))
            reply.downvotes.add(Users.getUserById(userId)!!)
            return
        }

        if (reply.downvotes.contains(Users.getUserById(userId))){
            reply.downvotes.remove(Users.getUserById(userId))
        } else {
            reply.downvotes.add(Users.getUserById(userId)!!)
        }
        //change karma to reflect decision

    }
}