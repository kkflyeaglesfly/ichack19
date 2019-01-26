package cogni.cogni

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class PostController {
    @GetMapping("/")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            "Hello, $name"

    @GetMapping("/post")
    fun getPost(@RequestParam(value = "postId") postId: Int) {

    }
}