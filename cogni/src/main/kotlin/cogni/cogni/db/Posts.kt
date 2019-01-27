package cogni.cogni.db

import cogni.cogni.model.Post
import cogni.cogni.model.Reply
import cogni.cogni.model.User

object Posts {

    val POST_REPORT_MAX : Int = 50
    val REPLY_REPORT_MAX : Int = 25


    //var posts: MutableList<Post> = mutableListOf(Post(0, 0, 100, "Welcome", "Welcome to Cogni!", mutableListOf(), mutableListOf(), mutableListOf(Users.users.get(1))))
    var posts: MutableList<Post> = mutableListOf(Post(0, 0, mutableListOf(), "Welcome to Cogni!", "thanks i'm cured", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf()))

    fun getPostById(id: Long) : Post? {
        return posts.find { post : Post -> post.id == id}
    }

    fun reply(postId: Long, userId: Long, body: String): Int {
        val post: Post? = getPostById(postId)
        if (post != null) {

            post.replies.add(Reply(post.replies.size.toLong(), userId, "anon", body, mutableListOf(), mutableListOf(), mutableListOf()))
            return 0
        }
        return -1
    }

    fun getReplyById(post: Post, replyId: Long) : Reply? {
        return post.replies.find { reply -> reply.id == replyId }
    }

    fun reportReply(postId: Long, replyId: Long, userId: Long): Int {
        val post: Post? = getPostById(postId)
        if (post != null) {
            val reply: Reply? = getReplyById(post, replyId)
            val downvoter = Users.getUserById(userId)
            if (reply != null && downvoter != null && !reply.reports.contains(downvoter)) {
                reply.reports.add(downvoter)
                if (reply.reports.size >= REPLY_REPORT_MAX) {
                    // mark reply invis
                    for (reporter in reply.reports) {
                        Users.karma(reporter, 25)
                    }
                    Users.karma(reply.userId, -100)
                }
                return 0
            }
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

    fun reportPost(postId: Long, userId: Long): Int {
        val post: Post? = getPostById(postId)
        val downvoter = Users.getUserById(userId)
        if (post != null && downvoter != null && !post.reports.contains(downvoter)) {
            post.reports.add(downvoter)
            if (post.reports.size >= POST_REPORT_MAX) {
                // mark post invis
                for (reporter in post.reports) {
                    Users.karma(reporter, 25)
                }
                Users.karma(post.userId, -75)
            }
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
        //mark post as removed
        //posts.remove(getPostById(postId))
    }

    fun upvotePost(postId: Long, userId : Long){
        val post : Post = getPostById(postId)!!
        if (post.upvotes.contains(Users.getUserById(userId))){
            post.upvotes.remove(Users.getUserById(userId))
        } else {
            post.upvotes.add(Users.getUserById(userId)!!)
        }
        Users.karma(userId, 3)
        Users.karma(post.userId, 10)
    }

    fun upvoteReply(replyId: Long, userId: Long, post: Post){
        var reply : Reply = getReplyById(post, replyId)!!

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
        Users.karma(userId, 5)
        Users.karma(reply.userId, 20)

    }

    fun downVoteReply(replyId: Long, userId: Long, post: Post){
        var reply : Reply = getReplyById(post, replyId)!!

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
        Users.karma(userId, 25)
        Users.karma(reply.userId, -25)
    }
}