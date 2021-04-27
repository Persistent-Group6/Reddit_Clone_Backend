package com.grp6.reddit_clone.controller;

import com.grp6.reddit_clone.dto.PostResponse;
import com.grp6.reddit_clone.security.JwtProvider;
import com.grp6.reddit_clone.service.PostService;
import com.grp6.reddit_clone.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("Should List All Posts When making GET request to endpoint - /api/posts/")
    public void shouldCreatePost() throws Exception {
        PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://xyz.com", "Description", "User 1",
                "Sub1", 0, 0, "1 day ago", false, false);
        PostResponse postRequest2 = new PostResponse(2L, "Another Post", "http://abc.com", "Description++", "User 2",
                "Sub2", 0, 0, "2 days ago", false, false);

        Mockito.when(postService.getAllPosts()).thenReturn(asList(postRequest1, postRequest2));

        mockMvc.perform(get("/api/posts/"))
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
}
