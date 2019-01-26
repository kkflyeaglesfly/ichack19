package cogni.cogni

import cogni.cogni.db.Posts
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @GetMapping("/")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            "Hello, $name"

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Long) =
            Posts.getPostById(postId)
}