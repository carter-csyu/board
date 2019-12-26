package com.carter.book.web;

import com.carter.book.service.posts.PostsService;
import com.carter.book.web.dto.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping
    public Long save(@RequestBody PostsRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}
