package com.carter.book.service.posts;

import com.carter.book.domain.posts.Posts;
import com.carter.book.domain.posts.PostsRepository;
import com.carter.book.web.dto.PostsListResponseDto;
import com.carter.book.web.dto.PostsRequestDto;
import com.carter.book.web.dto.PostsResponseDto;
import com.carter.book.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(String.format("해당 사용자가 없습니다. id: %d", id)));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(String.format("해당 사용자가 없습니다. id: %d", id)));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
            .map(PostsListResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(String.format("해당 사용자가 없습니다. id: %d", id)));

        postsRepository.delete(posts);
    }
}
