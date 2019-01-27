package cogni.cogni

import cogni.cogni.db.Posts
import cogni.cogni.db.Users
import cogni.cogni.http.*
import cogni.cogni.model.FriendRequest
import cogni.cogni.model.User
import cogni.cogni.model.UserType
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @RequestMapping(value = ["/signup"], method = arrayOf(RequestMethod.POST))
    fun signup(@RequestBody createUserReq: CreateUserReq) : LoginRes {
        val user: User = Users.createUser(createUserReq.email, createUserReq.name, createUserReq.password)
        return LoginRes(user.id, false, 0)
    }

    @RequestMapping(value = ["/signin"], method = arrayOf(RequestMethod.POST))
    fun signin(@RequestBody loginReq: LoginReq) : LoginRes {
        val user: User? = Users.getUserByEmailPassword(loginReq.email, loginReq.password)
        if (user != null)
            return LoginRes(user.id, user.userType == UserType.ADMIN, 0)
        return LoginRes(-1, user.userType == UserType.ADMIN, -1)
    }

    @PostMapping("/friend/poster")
    fun friendRequest(@RequestBody friendReq : FriendReq): Res {
        if (Posts.containsReplyFrom(friendReq.postId, friendReq.toUserId)) {
            return Res(Users.friendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId))
        }

        return Res(-1)
    }

    @PostMapping("/friend/replier")
    fun sendRequestByReplier(@RequestBody friendReq : FriendReq): Res {
        if (Posts.containsReplyFrom(friendReq.postId, friendReq.fromUserId)) {
            return Res(Users.friendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId))
        }
        return Res(-1)
    }

    @PostMapping("/friend/accept")
    fun acceptRequest(@RequestBody friendReq : FriendReq) : Res {
        return Res(Users.acceptFriend(friendReq.fromUserId, friendReq.toUserId, friendReq.postId))
    }

    @PostMapping("/friend/reject")
    fun rejectRequest(@RequestBody friendReq : FriendReq) : Res {
        return Res(Users.rejectFriend(friendReq.fromUserId, friendReq.toUserId, friendReq.postId))
    }
}