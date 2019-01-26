package cogni.cogni.db

import cogni.cogni.model.FriendRequest
import cogni.cogni.model.FriendRequestStatus

object FriendRequests {
    var friendRequests: List<FriendRequest> = listOf(FriendRequest(0, 1, 0, FriendRequestStatus.SENT))


}