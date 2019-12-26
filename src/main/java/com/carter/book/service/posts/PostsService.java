package com.carter.book.service.posts;

import com.carter.book.domain.posts.PostsRepository;
import com.carter.book.web.dto.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
