package com.grp6.reddit_clone.controller;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grp6.reddit_clone.dto.PostResponse;
import com.grp6.reddit_clone.security.JwtProvider;
import com.grp6.reddit_clone.service.PostService;
import com.grp6.reddit_clone.service.UserDetailsServiceImpl;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

        @MockBean
        private PostService postService;
        @MockBean
        private UserDetailsServiceImpl userDetailsService;
        @MockBean
        private JwtProvider jwtProvider;

        @Autowired
        private MockMvc mockMvc;

        @Test
        @DisplayName("Should list all posts when making GET request to endpoint - /api/posts/")
        public void shouldGetAllPosts() throws Exception {
                PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://xyz.com",
                                "Description", "User 1", "Sub1", 0, 0, "1 day ago", false, false);
                PostResponse postRequest2 = new PostResponse(2L, "Another Post", "http://abc.com",
                                "Description++", "User 2", "Sub2", 0, 0, "2 days ago", false,
                                false);

                Mockito.when(postService.getAllPosts())
                                .thenReturn(asList(postRequest1, postRequest2));

                mockMvc.perform(get("/api/posts/")).andExpect(status().is(200))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                                .andExpect(jsonPath("$[0].postName", Matchers.is("Post Name")))
                                .andExpect(jsonPath("$[0].url", Matchers.is("http://xyz.com")))
                                .andExpect(jsonPath("$[1].url", Matchers.is("http://abc.com")))
                                .andExpect(jsonPath("$[1].postName", Matchers.is("Another Post")))
                                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
        }

        @Test
        @DisplayName("Should return post with Id = {id} when making GET request to endpoint - /api/posts/{id}")
        public void shouldGetPostById() throws Exception {
                Long postId = 122L;

                PostResponse postResponse = new PostResponse(postId, "Test Post Name",
                                "http://abc.com", "Description", "Test User", "Sub1", 0, 0,
                                "1 day ago", false, false);

                Mockito.when(postService.getPost(postId)).thenReturn(postResponse);

                mockMvc.perform(get("/api/posts/" + postId)).andExpect(status().is(200))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("id", Matchers.is(122)))
                                .andExpect(jsonPath("postName", Matchers.is("Test Post Name")))
                                .andExpect(jsonPath("url", Matchers.is("http://abc.com")));
        }

        @Test
        @DisplayName("Should return post with Subreddit Id = {id} when making GET request to endpoint - /api/posts/by-subreddit/{id}")
        public void shouldGetPostBySubredditId() throws Exception {
                PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://xyz.com",
                                "Description", "User 1", "Sub1", 0, 0, "1 day ago", false, false);
                PostResponse postRequest2 = new PostResponse(2L, "Another Post", "http://abc.com",
                                "Description++", "User 2", "Sub1", 0, 0, "2 days ago", false,
                                false);
                Long subredditId = 1L;
                Mockito.when(postService.getPostsBySubreddit(subredditId))
                                .thenReturn(asList(postRequest1, postRequest2));

                mockMvc.perform(get("/api/posts/by-subreddit/" + subredditId))
                                .andExpect(status().is(200))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                                .andExpect(jsonPath("$[0].postName", Matchers.is("Post Name")))
                                .andExpect(jsonPath("$[0].url", Matchers.is("http://xyz.com")))
                                .andExpect(jsonPath("$[1].url", Matchers.is("http://abc.com")))
                                .andExpect(jsonPath("$[1].postName", Matchers.is("Another Post")))
                                .andExpect(jsonPath("$[1].id", Matchers.is(2)));

        }

        @Test
        @DisplayName("Should return post with User Id = {id} when making GET request to endpoint - /api/posts/by-user/{id}")
        public void shouldGetPostByUsername() throws Exception {
                PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://xyz.com",
                                "Description", "User 1", "Sub1", 0, 0, "1 day ago", false, false);
                PostResponse postRequest2 = new PostResponse(2L, "Test Post Name", "http://abc.com",
                                "Description", "User 1", "Sub1", 0, 0, "1 day ago", false, false);

                String username = "User 1";
                Mockito.when(postService.getPostsByUsername(username))
                                .thenReturn(asList(postRequest1, postRequest2));

                mockMvc.perform(get("/api/posts/by-user/" + username)).andExpect(status().is(200))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                                .andExpect(jsonPath("$[0].postName", Matchers.is("Post Name")))
                                .andExpect(jsonPath("$[0].url", Matchers.is("http://xyz.com")))
                                .andExpect(jsonPath("$[0].userName", Matchers.is("User 1")))
                                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                                .andExpect(jsonPath("$[1].postName", Matchers.is("Test Post Name")))
                                .andExpect(jsonPath("$[1].url", Matchers.is("http://abc.com")))
                                .andExpect(jsonPath("$[1].userName", Matchers.is("User 1")));

        }

}
