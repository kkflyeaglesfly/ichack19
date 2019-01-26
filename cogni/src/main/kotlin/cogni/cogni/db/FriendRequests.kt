package cogni.cogni.db

import cogni.cogni.model.FriendRequest
import cogni.cogni.model.FriendRequestStatus

object FriendRequests {
    var friendRequests = mutableListOf(FriendRequest(0, 1, 0))


    fun acceptStatus(friendRequest: FriendRequest) : Int {
        if (!friendRequests.contains(friendRequest)){
            return -1
        }
        return 0
    }

    fun createFriendRequest(friendRequest: FriendRequest) : Int{
        return if (friendRequests.contains(friendRequest)){
            -1
        } else{
            friendRequests.add(friendRequest)
            0
        }
    }

    fun addFriend(friendRequest: FriendRequest) : Int {
        return if (Users.getUserById(friendRequest.fromUserId) == null){
            -1
        } else {
            Posts.getPostById(friendRequest.postId)!!.friends!!.add(Users.getUserById(friendRequest.fromUserId)!!)
            0
        }
    }

    fun rejectFriend(friendRequest: FriendRequest) : Int {
        return if (!friendRequests.remove(friendRequest)){
            -1
        } else {
            0
        }
    }

}