package cogni.cogni.db

import cogni.cogni.model.Post

object Posts {
    var posts: List<Post> = listOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", null, null))

    fun getPostById(id: Long) : Post? {
       val post: Post? = posts.find { post -> post.id == id}

        return post
    }
}