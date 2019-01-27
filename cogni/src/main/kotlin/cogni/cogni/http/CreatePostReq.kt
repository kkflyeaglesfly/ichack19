package cogni.cogni.http

import cogni.cogni.model.PostCategory

data class CreatePostReq (
        val userId: Long,
        val title: String,
        val body: String,
        val category: PostCategory
)