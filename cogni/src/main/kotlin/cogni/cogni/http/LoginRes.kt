package cogni.cogni.http

data class LoginRes(
        val userId: Long,
        val isAdmin: Boolean,
        val res: Int
)