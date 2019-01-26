package cogni.cogni

import cogni.cogni.db.Posts
import cogni.cogni.http.*
import cogni.cogni.model.Post
import cogni.cogni.model.Reply
import cogni.cogni.model.User
import org.springframework.web.bind.annotation.*

@RestController
class PostController {

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Long, @RequestParam(value = "userId") userId: Long) =
            Posts.getPostById(postId)

    @RequestMapping(value = ["/reply"], method = arrayOf(RequestMethod.POST))
    fun reply(@RequestBody replyReq: ReplyReq): Res {
        val reply = Reply(replyReq.userId, replyReq.body, 0, 0)
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