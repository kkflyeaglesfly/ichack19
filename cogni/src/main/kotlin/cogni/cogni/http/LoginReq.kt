package cogni.cogni.http

import com.fasterxml.jackson.annotation.JsonCreator

data class LoginReq @JsonCreator constructor(
        val email: String,
        val password: String
)