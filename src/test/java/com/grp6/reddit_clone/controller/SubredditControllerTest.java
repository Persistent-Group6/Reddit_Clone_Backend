package com.grp6.reddit_clone.controller;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grp6.reddit_clone.dto.SubredditDto;
import com.grp6.reddit_clone.security.JwtProvider;
import com.grp6.reddit_clone.service.SubredditService;
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

@WebMvcTest(controllers = SubredditController.class)
public class SubredditControllerTest {

    @MockBean
    private SubredditService subredditService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should list all subreddit when making GET request to endpoint - /api/subreddit")
    public void shouldGetAllSubreddit() throws Exception {
        SubredditDto response1 = new SubredditDto(1L, "Subreddit 1", "Description", 10);
        SubredditDto response2 = new SubredditDto(2L, "Subreddit 2", "Description", 8);

        Mockito.when(subredditService.getAll()).thenReturn(asList(response1, response2));

        mockMvc.perform(get("/api/subreddit")).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Subreddit 1")))
                .andExpect(jsonPath("$[0].description", Matchers.is("Description")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Subreddit 2")))
                .andExpect(jsonPath("$[1].description", Matchers.is("Description")));
    }

    // @Test
    // @DisplayName("Should get subreddit with ID = {id} when making GET request to endpoint - /api/subreddit/{id}")
    // public void shouldGetSubreddit() throws Exception {
    //     Long subredditId = 1L;
    //     SubredditDto response1 = new SubredditDto(subredditId, "Subreddit 1", "Description", 10);

    //     Mockito.when(subredditService.getSubreddit(subredditId)).thenReturn(response1);

    //     mockMvc.perform(get("/api/subreddit/" + subredditId)).andExpect(status().is(200))
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("id", Matchers.is(1)))
    //             .andExpect(jsonPath("name", Matchers.is("Subreddit 1")))
    //             .andExpect(jsonPath("description", Matchers.is("Description")));
    // }
}
