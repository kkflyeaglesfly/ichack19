package cogni.cogni

import cogni.cogni.db.Posts
import cogni.cogni.db.Users
import cogni.cogni.http.*
import cogni.cogni.model.FriendRequest
import cogni.cogni.model.User
import org.springframework.web.bind.annotation.*

@RestController
class AdminController {

    @RequestMapping(value = ["/post/delete"], method = arrayOf(RequestMethod.POST))
    fun deletePost(@RequestBody deletePostReq: DeletePostReq) : Res {
        val userIfAdmin = Users.userIfAdmin(deletePostReq.userId)
        if (userIfAdmin != null && Posts.removePost(deletePostReq.postId)) {
            return Res(0)
        }
        return Res(-1)
    }

    @RequestMapping(value = ["/reply/delete"], method = arrayOf(RequestMethod.POST))
    fun deleteReply(@RequestBody deleteReplyReq: DeleteReplyReq) : Res {
        val userIfAdmin = Users.userIfAdmin(deleteReplyReq.userId)
        if (userIfAdmin != null && Posts.removeReply(deleteReplyReq.postId, deleteReplyReq.replyId)) {
            return Res(0)
        }
        return Res(-1)
    }

    @PostMapping("/post/lock")
    fun lockPost(@RequestBody lockPostReq : LockPostReq): Res {
        val userIfAdmin = Users.userIfAdmin(lockPostReq.userId)
        if (userIfAdmin != null && Posts.lockPost(lockPostReq.postId)) {
            return Res(0)
        }
        return Res(-1)
    }
}
