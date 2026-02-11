package com.example.started.modules.posts;

import com.example.started.modules.auth.util.IpUtils;
import com.example.started.common.v0.utils.Result;
import com.example.started.demo.cache.RedisUtils;
import com.example.started.modules.auth.validate.annotation.LoginUserId;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 帖子
 */
@Log4j2
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final RedisUtils redisUtils;

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

    @PostMapping(value = "create")
    public Result<?> create(HttpServletRequest httpRequest, @LoginUserId TokenUserId userId, @RequestBody PostsCreateBo body) {
        if (redisUtils.lock("ostsController:create:" + IpUtils.getIpAddr(httpRequest), 1, TimeUnit.HOURS)) {
            return Result.error("您的ip最近有发帖，请稍后再发吧");
        }
        postsService.create(userId, body);
        return Result.ok();
    }

}
