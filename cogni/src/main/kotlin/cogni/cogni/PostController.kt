package cogni.cogni

import cogni.cogni.db.Posts
import cogni.cogni.http.ReplyReq
import cogni.cogni.model.Reply
import org.springframework.web.bind.annotation.*

@RestController
class PostController {

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Long) =
            Posts.getPostById(postId)

    @PatchMapping("/reply")
    fun reply(@RequestBody replyReq: ReplyReq) {
        val reply = Reply(replyReq.userId, replyReq.body, 0, 0)
        Posts.reply(replyReq.postId, reply)
    }

}