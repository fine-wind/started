package com.example.started.modules.posts;

import com.example.started.common.v0.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帖子
 */
@Log4j2
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping(value = "find/list")
    public Result<List<PostsFindVo>> index(@RequestBody PostsFindBo body) {
        List<PostsFindVo> list = postsService.find(body);
        return Result.ok(list);
    }

    @GetMapping(value = "find/info/{id}")
    public Result<PostsFindVo> info(@PathVariable("id") String id) {
        return Result.ok(postsService.info(id));
    }

    @GetMapping(value = "find/info/comment/{id}")
    public Result<List<PostsInfoCommentVo>> infoComment(@PathVariable("id") String id) {
        return Result.ok(postsService.infoComment(id));
    }

}
