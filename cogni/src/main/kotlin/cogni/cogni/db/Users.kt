package cogni.cogni.db

import cogni.cogni.model.FriendRequest
import cogni.cogni.model.User
import cogni.cogni.model.UserType

object Users {
    var users: MutableList<User> = mutableListOf(
            User(0, "sam@cogni.com", "Sam Liem", "donald", UserType.ADMIN, mutableListOf()),
            User(1, "dhru@cogni.com", "Dhru Devalia", "trustno1", UserType.ADMIN, mutableListOf())
    )

    fun getUserByEmailPassword(email: String, password: String): User? {
        return users.find { user -> user.email == email && user.password == password }
    }

    fun getUserById(id: Long): User? {
        return users.find { user -> user.id == id }
    }

    fun createUser(email: String, name: String, password: String, type: UserType = UserType.GENERAL): User {
        val user = User(users.size.toLong(), email, name, password, type, mutableListOf())
        users.add(user)
        return user
    }

    fun isValidUserId(userId: Long) : Boolean {
        if (userId < 0){
            return false
        }

        for (u in users){
            if (u.id == userId){
                return true
            }
        }
        return false
    }

    fun getFriendRequest(fromUserId: Long, toUser: User, postId: Long) : FriendRequest? =
        toUser.friendRequests.find { friendRequest -> friendRequest.fromUserId == fromUserId && friendRequest.postId == postId }

    fun hasFriendRequestFrom(fromUserId: Long, toUser: User, postId: Long) =
        getFriendRequest(fromUserId, toUser, postId) != null

    fun acceptFriend(fromUserId: Long, toUserId: Long, postId: Long) : Int {
        val to = getUserById(toUserId)!!
        val friendRequest : FriendRequest = getFriendRequest(fromUserId, to, postId) ?: return -1
        to.friendRequests.remove(friendRequest)
        val post = Posts.getPostById(friendRequest.postId)!!

        if (post.userId == toUserId)
            post.friends.add(getUserById(fromUserId)!!)
        else
            post.friends.add(to)
        return 0
    }

    fun rejectFriend(fromUserId: Long, toUserId: Long, postId: Long) : Int {
        val to = getUserById(toUserId)!!
        val friendRequest : FriendRequest = getFriendRequest(fromUserId, to, postId) ?: return -1
        to.friendRequests.remove(friendRequest)
        return 0
    }

    fun friendRequest(fromUserId: Long, toUserId: Long, postId: Long) : Int {
        val to = getUserById(toUserId)!!
        if (hasFriendRequestFrom(fromUserId, to, postId)) {
            return -1
        }
        to.friendRequests.add(FriendRequest(fromUserId, postId))
        return 0
    }
}