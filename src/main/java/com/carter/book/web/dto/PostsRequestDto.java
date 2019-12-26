package com.carter.book.web.dto;

import com.carter.book.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsRequestDto {
    private String title;
    private String content;
    private String author;
    public Posts toEntity() {
        return Posts.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
    }

    @Builder
    public PostsRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
