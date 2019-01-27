package cogni.cogni.db

import cogni.cogni.model.Post
import cogni.cogni.model.Reply

object Posts {
    //var posts: MutableList<Post> = mutableListOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", mutableListOf(), mutableListOf(), mutableListOf(Users.users.get(1))))
    var posts: MutableList<Post> = mutableListOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", mutableListOf(), mutableListOf(), mutableListOf()))
    
    fun getPostById(id: Long) : Post? {
        return posts.find { post -> post.id == id}
    }

    fun reply(postId: Long, userId: Long, body: String): Int {
        val post: Post? = getPostById(postId)
        if (post != null) {
            post.replies.add(Reply(post.replies.size.toLong(), userId, "anon", body, 0, 0))
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

}