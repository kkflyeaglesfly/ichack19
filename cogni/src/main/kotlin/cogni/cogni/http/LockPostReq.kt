package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class LockPostReq @JsonCreator constructor(
        val postId: Long,
        val userId: Long
)