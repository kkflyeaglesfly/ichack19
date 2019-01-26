package cogni.cogni.db

import cogni.cogni.model.Post
import cogni.cogni.model.Reply

object Posts {
    var posts: List<Post> = listOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", null, null, null))

    fun getPostById(id: Long) : Post? {
        return posts.find { post -> post.id == id}
    }

    fun reply(id: Long, reply: Reply) {
        var post: Post? = getPostById(id)
        if (post != null) {
            if (post.replies == null) {
                post.replies = mutableListOf(reply)
            } else {
                post.replies!!.add(reply)
            }
        }
    }
}