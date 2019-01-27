package cogni.cogni

import cogni.cogni.db.FriendRequests
import cogni.cogni.db.Posts
import cogni.cogni.http.FriendReq
import cogni.cogni.http.Res
import cogni.cogni.model.FriendRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FriendRequestController {

    @PostMapping("/friend/sendPoster")
    fun sendRequestByPoster(@RequestBody friendReq : FriendReq): Res {
        if (Posts.containsReplier(friendReq.toUserId, friendReq.postId)) {
            var friendReqest = FriendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId)
            return Res(FriendRequests.createFriendRequest(friendReqest))
        }

        return Res(-1)
    }

    @PostMapping("/friend/sendReplier")
    fun sendRequestByReplier(@RequestBody friendReq : FriendReq): Res {
        if (Posts.containsReplier(friendReq.fromUserId, friendReq.postId)) {
            var friendReqest = FriendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId)
            return Res(FriendRequests.createFriendRequest(friendReqest))
        }
        return Res(-1)
    }

    @PostMapping("/friend/accept")
    fun acceptRequest(@RequestBody friendReq : FriendReq) : Res {
        var friendReqest = FriendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId)
        return Res(FriendRequests.acceptStatus(friendReqest))
    }

    @PostMapping("/friend/reject")
    fun rejectRequest(@RequestBody friendReq : FriendReq) : Res {
        var friendReqest = FriendRequest(friendReq.fromUserId, friendReq.toUserId, friendReq.postId)
        return Res(FriendRequests.acceptStatus(friendReqest))
    }



}

