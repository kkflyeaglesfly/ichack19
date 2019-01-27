package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class ReportPostReq @JsonCreator constructor(
        val postId: Long,
        val userId: Long
)