package cogni.cogni.db

import java.io.File
import java.io.InputStream

import cogni.cogni.model.Post
import cogni.cogni.model.Reply

object Posts {
    var posts = mutableListOf<Post>(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", null, null, null))

    fun getPostById(id: Long): Post? {
        return posts.find { post -> post.id == id }
    }

    fun reply(id: Long, reply: Reply): Int {
        var post: Post? = getPostById(id)
        if (profanityCheck(reply)) {
            if (post != null) {
                if (post.replies == null) {
                    post.replies = mutableListOf(reply)
                } else {
                    post.replies!!.add(reply)
                }
                return 0
            }
        }
        return -1
    }

    // Iterate through the textfile using inputstream
    fun profanityCheck(reply: Reply): Boolean {
        var inputStream: InputStream = File("profanityList.txt").inputStream()
        var words = inputStream.readBytes().toString(Charset.defaultCharset())

        return true
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