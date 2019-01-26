package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class FollowupReq @JsonCreator constructor(
        val postId: Long,
        val userId: Long,
        val body: String
)