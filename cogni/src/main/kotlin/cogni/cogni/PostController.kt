package cogni.cogni

import cogni.cogni.db.Posts
import cogni.cogni.db.Users
import cogni.cogni.http.*
import cogni.cogni.model.Post
import cogni.cogni.model.Reply
import cogni.cogni.model.User
import org.springframework.web.bind.annotation.*

@RestController
class PostController {

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Long, @RequestParam(value = "userId") userId: Long) : GetPostRes {
        return getPostMapper(Posts.getPostById(postId)!!, userId)
    }

    fun isAFriend(friends: List<User>, friendId: Long) =
        friends.map { friend -> friend.id }.contains(friendId)

    fun getPostMapper(post: Post, userId: Long) : GetPostRes {
        // If the poster is friends
        val isOwner = post.userId == userId
        val owner : User = Users.getUserById(post.userId)!!
        var name = "anon"
        var replies : MutableList<Reply> = ArrayList()
        if (isOwner) {
            if (post.replies != null && post.replies!!.size > 0) {
                for (reply in post.replies!!) {
                    if (post.friends != null && isAFriend(post.friends, reply.userId)) {
                        val friend = Users.getUserById(reply.userId)
                        replies.add(reply.copy(name = friend!!.name))
                    } else {
                        replies.add(reply)
                    }
                }
            }
            name = owner.name + "(anon to strangers)"
        } else {
            if (post.replies != null && post.replies!!.size > 0) {
                val reader : User = Users.getUserById(userId)!!
                for (reply in post.replies!!) {
                    if (reply.userId == userId) {
                        replies.add(reply.copy(name = reader.name))
                    } else {
                        replies.add(reply)
                    }
                }
            }
            if (post.friends != null && isAFriend(post.friends, userId)) {
                name = owner.name + "(anon to strangers)"
            }
        }

        return GetPostRes(post.id, post.userId, name, post.upvotes, post.title, post.body, post.followUps, replies)
    }

    @RequestMapping(value = ["/reply"], method = arrayOf(RequestMethod.POST))
    fun reply(@RequestBody replyReq: ReplyReq) : Res {
        val reply = Reply(replyReq.userId, "anon", replyReq.body, 0, 0)
        return Res(Posts.reply(replyReq.postId, reply))
    }

    @RequestMapping(value = ["/followup"], method = arrayOf(RequestMethod.POST))
    fun followup(@RequestBody followupReq: FollowupReq): Res {
        return Res(Posts.followup(followupReq.userId, followupReq.postId, followupReq.body))
    }

    @PostMapping("/post")
    fun createPost(@RequestBody createPostReq: CreatePostReq): Res {
        val newPost = Post(Posts.getUniqueId().toLong(), createPostReq.userId, 0, createPostReq.title,
                createPostReq.body, mutableListOf<String>(), mutableListOf<Reply>(), mutableListOf<User>())
        return Res(Posts.createPost(newPost))
    }


}