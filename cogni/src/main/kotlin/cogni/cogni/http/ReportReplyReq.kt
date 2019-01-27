package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class ReportReplyReq @JsonCreator constructor(
        val postId: Long,
        val replyId: Long,
        val userId: Long
)