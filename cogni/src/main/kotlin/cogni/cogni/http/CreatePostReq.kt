package cogni.cogni.http

data class CreatePostReq (
        val userId: Long,
        val title: String,
        val body: String
)