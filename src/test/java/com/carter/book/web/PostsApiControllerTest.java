package com.carter.book.web;

import com.carter.book.domain.posts.Posts;
import com.carter.book.domain.posts.PostsRepository;
import com.carter.book.web.dto.PostsRequestDto;
import com.carter.book.web.dto.PostsResponseDto;
import com.carter.book.web.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String author = "author";
        PostsRequestDto requestDto = PostsRequestDto.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();

        String url = String.format("http://localhost:%d/api/v1/posts", port);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody() > 0L);

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        assertEquals(posts.getTitle(), title);
        assertEquals(posts.getContent(), content);
        assertEquals(posts.getAuthor(), author);
    }

    @Test
    public void Posts_수정된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                                                    .title("title")
                                                    .content("content")
                                                    .author("author").build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "new title";
        String expectedContent = "new Content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
            .title(expectedTitle)
            .content(expectedContent)
            .build();

        String url = String.format("http://localhost:%d/api/v1/posts/%d", port, updateId);

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody() > 0L);

        List<Posts> all = postsRepository.findAll();
        assertEquals(all.get(0).getTitle(), expectedTitle);
        assertEquals(all.get(0).getContent(), expectedContent);
    }

    @Test
    public void Posts_삭제된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                                                    .title("title")
                                                    .content("content")
                                                    .author("author").build());

        List<Posts> postsList = postsRepository.findAll();
        assertEquals(postsList.size(), 1);

        String url = String.format("http://localhost:%d/api/v1/posts/%d", port, savedPosts.getId());

        // when
        restTemplate.delete(url);

        // then
        postsList = postsRepository.findAll();
        assertEquals(postsList.size(), 0);
    }
}