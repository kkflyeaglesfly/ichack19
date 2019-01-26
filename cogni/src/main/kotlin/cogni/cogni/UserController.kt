package cogni.cogni

import cogni.cogni.db.Users
import cogni.cogni.http.CreateUserReq
import cogni.cogni.http.LoginReq
import cogni.cogni.http.LoginRes
import cogni.cogni.model.User
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @PostMapping("/signup")
    fun signup(@RequestBody createUserReq: CreateUserReq) : LoginRes {
        val user: User = Users.createUser(createUserReq.email, createUserReq.name, createUserReq.password)
        return LoginRes(user.id, 0)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody loginReq: LoginReq) : LoginRes {
        val user: User? = Users.getUserByEmailPassword(loginReq.email, loginReq.password)
        if (user != null)
            return LoginRes(user.id, 0)
        return LoginRes(-1, -1)
    }
}