package com.grp6.reddit_clone.service;

import static java.util.Arrays.asList;

import com.grp6.reddit_clone.dto.PostRequest;
import com.grp6.reddit_clone.dto.PostResponse;
import com.grp6.reddit_clone.mapper.PostMapper;
import com.grp6.reddit_clone.model.Post;
import com.grp6.reddit_clone.model.Subreddit;
import com.grp6.reddit_clone.model.User;
import com.grp6.reddit_clone.repository.PostRepository;
import com.grp6.reddit_clone.repository.SubredditRepository;
import com.grp6.reddit_clone.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

        @Mock
        private PostRepository postRepository;
        @Mock
        private SubredditRepository subredditRepository;
        @Mock
        private UserRepository userRepository;
        @Mock
        private AuthService authService;
        @Mock
        private PostMapper postMapper;

        @Captor
        private ArgumentCaptor<Post> postArgumentCaptor;

        private PostService postService;

        @BeforeEach
        public void setup() {
                postService = new PostService(postRepository, subredditRepository, userRepository,
                                authService, postMapper);
        }


        @Test
        @DisplayName("Should Save Posts")
        public void shouldSavePosts() {
                User currentUser = new User(123L, "test user", "secret password", "user@email.com",
                                Instant.now(), true);
                Subreddit subreddit = new Subreddit(123L, "First Subreddit",
                                "Subreddit Description", emptyList(), Instant.now(), currentUser);
                Post post = new Post(123L, "First Post", "http://url.site", "Test", 0, null,
                                Instant.now(), null);
                PostRequest postRequest = new PostRequest(null, "First Subreddit", "First Post",
                                "http://url.site", "Test");

                Mockito.when(subredditRepository.findByName("First Subreddit"))
                                .thenReturn(Optional.of(subreddit));
                Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
                Mockito.when(postMapper.map(postRequest, subreddit, currentUser)).thenReturn(post);

                postService.save(postRequest);
                Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

                Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
                Assertions.assertThat(postArgumentCaptor.getValue().getPostName())
                                .isEqualTo("First Post");
        }

        @Test
        @DisplayName("Should Retrieve Post by Id")
        public void shouldFindPostById() {
                Post post = new Post(123L, "First Post1", "http://googlee.com", "Test", 0, null,
                                Instant.now(), null);
                PostResponse expectedPostResponse = new PostResponse(123L, "First Post",
                                "http://googlee.com", "Test", "Test User", "Test Subredit", 0, 0,
                                "1 Hour Ago", false, false);

                Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
                Mockito.when(postMapper.mapToDto(Mockito.any(Post.class)))
                                .thenReturn(expectedPostResponse);

                PostResponse actualPostResponse = postService.getPost(123L);

                Assertions.assertThat(actualPostResponse.getId())
                                .isEqualTo(expectedPostResponse.getId());
                Assertions.assertThat(actualPostResponse.getPostName())
                                .isEqualTo(expectedPostResponse.getPostName());
                Assertions.assertThat(actualPostResponse.getUrl())
                                .isEqualTo(expectedPostResponse.getUrl());

        }

        @Test
        @DisplayName("Should Retrieve All Posts")
        public void shouldGetAllPosts() {
                Post post1 = new Post(101L, "First Post", "http://googlee.com", "Test1", 0, null,
                                Instant.now(), null);
                Post post2 = new Post(102L, "Second Post", "http://googlee.com", "Test2", 0, null,
                                Instant.now(), null);

                PostResponse expectedResponse1 = new PostResponse(101L, "First Post",
                                "http://googlee.com", "Test1", "Test User", "Test Subredit", 0, 0,
                                "1 Hour Ago", false, false);

                PostResponse expectedResponse2 = new PostResponse(102L, "Second Post",
                                "http://googlee.com", "Test2", "Test User", "Test Subredit", 0, 0,
                                "1 Hour Ago", false, false);


                Mockito.when(postRepository.findAll()).thenReturn(asList(post1, post2));
                Mockito.when(postMapper.mapToDto(post1)).thenReturn(expectedResponse1);
                Mockito.when(postMapper.mapToDto(post2)).thenReturn(expectedResponse2);

                List<PostResponse> actualPostResponse = postService.getAllPosts();

                Assertions.assertThat(actualPostResponse.get(0).getId())
                                .isEqualTo(expectedResponse1.getId());
                Assertions.assertThat(actualPostResponse.get(0).getPostName())
                                .isEqualTo(expectedResponse1.getPostName());
                Assertions.assertThat(actualPostResponse.get(0).getUrl())
                                .isEqualTo(expectedResponse1.getUrl());

                Assertions.assertThat(actualPostResponse.get(1).getId())
                                .isEqualTo(expectedResponse2.getId());
                Assertions.assertThat(actualPostResponse.get(1).getPostName())
                                .isEqualTo(expectedResponse2.getPostName());
                Assertions.assertThat(actualPostResponse.get(1).getUrl())
                                .isEqualTo(expectedResponse2.getUrl());

        }

        @Test
        @DisplayName("Should Retrieve Post by Subreddit Id")
        public void shouldFindPostBySubredditId() {
                Post post = new Post(123L, "First Post", "http://googlee.com", "Test", 0, null,
                                Instant.now(), null);

                List<PostResponse> expectedPostResponse = asList(new PostResponse(123L,
                                "First Post", "http://googlee.com", "Test", "Test User",
                                "Test Subredit", 0, 0, "1 Hour Ago", false, false));

                Subreddit subreddit = new Subreddit(100L, "Test Subredit", "Test Subreddit", null,
                                null, null);

                Mockito.when(subredditRepository.findById(100L)).thenReturn(Optional.of(subreddit));
                Mockito.when(postRepository.findAllBySubreddit(subreddit)).thenReturn(asList(post));
                Mockito.when(postMapper.mapToDto(post)).thenReturn(expectedPostResponse.get(0));

                List<PostResponse> actualPostResponse = postService.getPostsBySubreddit(100L);

                Assertions.assertThat(actualPostResponse.get(0).getId())
                                .isEqualTo(expectedPostResponse.get(0).getId());
                Assertions.assertThat(actualPostResponse.get(0).getPostName())
                                .isEqualTo(expectedPostResponse.get(0).getPostName());
                Assertions.assertThat(actualPostResponse.get(0).getUrl())
                                .isEqualTo(expectedPostResponse.get(0).getUrl());

        }

        @Test
        @DisplayName("Should Retrieve Post by User Name")
        public void shouldFindPostByUserName() {
                Post post = new Post(123L, "First Post", "http://googlee.com", "Test", 0, null,
                                Instant.now(), null);

                List<PostResponse> expectedPostResponse = asList(new PostResponse(123L,
                                "First Post", "http://googlee.com", "Test", "Test User",
                                "Test Subredit", 0, 0, "1 Hour Ago", false, false));

                User user = new User(101L, "Test User", "#su2.ss", "test@user.com", null, true);

                Mockito.when(userRepository.findByUsername("Test User"))
                                .thenReturn(Optional.of(user));
                Mockito.when(postRepository.findByUser(user)).thenReturn(asList(post));
                Mockito.when(postMapper.mapToDto(post)).thenReturn(expectedPostResponse.get(0));

                List<PostResponse> actualPostResponse = postService.getPostsByUsername("Test User");

                Assertions.assertThat(actualPostResponse.get(0).getId())
                                .isEqualTo(expectedPostResponse.get(0).getId());
                Assertions.assertThat(actualPostResponse.get(0).getPostName())
                                .isEqualTo(expectedPostResponse.get(0).getPostName());
                Assertions.assertThat(actualPostResponse.get(0).getUrl())
                                .isEqualTo(expectedPostResponse.get(0).getUrl());

        }

}
