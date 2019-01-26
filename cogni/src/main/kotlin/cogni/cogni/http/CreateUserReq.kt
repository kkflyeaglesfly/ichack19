package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class CreateUserReq @JsonCreator constructor(
        val email: String,
        val password: String,
        val name: String
)