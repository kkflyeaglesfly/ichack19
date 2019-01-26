package cogni.cogni.db

import cogni.cogni.model.Post
import cogni.cogni.model.Reply

object Posts {
    var posts = mutableListOf<Post>(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", null, null, null))

    fun getPostById(id: Long): Post? {
        return posts.find { post -> post.id == id }
    }

    fun reply(id: Long, reply: Reply): Int {
        var post: Post? = getPostById(id)
        if (post != null) {
            if (post.replies == null) {
                post.replies = mutableListOf(reply)
            } else {
                post.replies!!.add(reply)
            }
            return 0
        }
        return -1
    }

    fun followup(userId: Long, id: Long, followup: String): Int {
        val post: Post? = getPostById(id)
        if (post != null && post.userId == userId) {
            if (post.followUps == null) {
                post.followUps = mutableListOf(followup)
            } else {
                post.followUps!!.add(followup)
            }
            return 0
        }
        return -1
    }

    fun createPost(newPost: Post): Int {
        if (Users.isValidUserId(newPost.userId)) {
            Posts.posts.add(newPost)
            return 0
        }
        return -1
    }

    fun getUniqueId(): Long {
        return Users.users.size.toLong()
    }
}