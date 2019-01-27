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

    @GetMapping("/posts")
    fun getPosts(@RequestParam(value = "userId") userId: Long) : GetPostsRes {
        return GetPostsRes(Posts.posts.sortedWith(Comparator{ a, b ->
            a.upvotes.size - b.upvotes.size
        }).reversed().map { p -> getPostMapper(p, userId) })
    }

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Long, @RequestParam(value = "userId") userId: Long) : GetPostRes {
        return getPostMapper(Posts.getPostById(postId)!!, userId)
    }

    @PostMapping("/post/report")
    fun reportPost(@RequestBody reportPostReq: ReportPostReq): Res {
        return Res(Posts.reportPost(reportPostReq.postId, reportPostReq.userId))
    }

    @PostMapping("/reply/report")
    fun reportReply(@RequestBody reportReplyReq: ReportReplyReq): Res {
        return Res(Posts.reportReply(reportReplyReq.postId, reportReplyReq.replyId, reportReplyReq.userId))
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
            if (post.replies.size > 0) {
                for (reply in post.replies) {
                    if (isAFriend(post.friends, reply.userId)) {
                        val friend = Users.getUserById(reply.userId)!!
                        replies.add(reply.copy(name = friend.name + " " + friend.email))
                    } else {
                        replies.add(reply)
                    }
                }
            }
            name = owner.name + "(anon to strangers)"
        } else {
            if (post.replies.size > 0) {
                val reader : User = Users.getUserById(userId)!!
                for (reply in post.replies) {
                    if (reply.userId == userId) {
                        replies.add(reply.copy(name = reader.name))
                    } else {
                        replies.add(reply)
                    }
                }
            }
            if (isAFriend(post.friends, userId)) {
                name = owner.name + " " + owner.email + "(anon to strangers)"
            }
        }

        return GetPostRes(post.id, post.userId, name, post.upvotes.size, post.title, post.body, post.followUps, replies, post.friends, post.reports)
    }

    @RequestMapping(value = ["/reply"], method = arrayOf(RequestMethod.POST))
    fun reply(@RequestBody replyReq: ReplyReq) : Res {
        return Res(Posts.reply(replyReq.postId, replyReq.userId, replyReq.body))

    }

    @RequestMapping(value = ["/followup"], method = arrayOf(RequestMethod.POST))
    fun followup(@RequestBody followupReq: FollowupReq): Res {
        return Res(Posts.followup(followupReq.userId, followupReq.postId, followupReq.body))
    }

    @PostMapping("/post")
    fun createPost(@RequestBody createPostReq: CreatePostReq): Res {
        val newPost = Post(Posts.getUniqueId().toLong(), createPostReq.userId, mutableListOf(), createPostReq.title,
                createPostReq.body, mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), createPostReq.category)
        return Res(Posts.createPost(newPost))
    }

    @PostMapping("/post/upvote")
    fun upvotePost(@RequestBody postReq: PostReq) : Res {
        Posts.upvotePost(postReq.postId, postReq.userId)
        return Res(0)
    }

    @PostMapping("/reply/upvote")
    fun upvoteReply(@RequestBody replyVoteReq: ReplyVoteReq) : Res {
        Posts.upvoteReply(replyVoteReq.replyId, replyVoteReq.userId, Posts.getPostById(replyVoteReq.postId)!!)
        return Res(0)
    }

    @PostMapping("/reply/downvote")
    fun downvoteReply(@RequestBody replyVoteReq: ReplyVoteReq) : Res {
        Posts.downVoteReply(replyVoteReq.replyId, replyVoteReq.userId, Posts.getPostById(replyVoteReq.postId)!!)
        return Res(0)
    }





}