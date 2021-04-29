package com.grp6.reddit_clone.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import com.grp6.reddit_clone.dto.SubredditDto;
import com.grp6.reddit_clone.mapper.SubredditMapper;
import com.grp6.reddit_clone.model.Subreddit;
import com.grp6.reddit_clone.repository.SubredditRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SubredditServiceTest {
    @Mock
    private SubredditRepository subredditRepository;

    @Mock
    private SubredditMapper subredditMapper;

    @Captor
    private ArgumentCaptor<Subreddit> subredditArgumentCaptor;

    private SubredditService subredditService;

    @BeforeEach
    public void setup() {
        subredditService = new SubredditService(subredditRepository, subredditMapper);
    }


    // @Test
    // @DisplayName("Should Save Subreddits")
    // public void shouldSaveSubreddits() {

    //     Subreddit subreddit = new Subreddit(100L, "Test Subredit", "Test Subreddit Description",
    //             null, null, null);

    //     SubredditDto subredditDto = new SubredditDto(100L, "Test Subredit", "Test Subreddit", 10);

    //     Mockito.when(subredditMapper.mapDtoToSubreddit(subredditDto)).thenReturn(subreddit);
    //     Mockito.when(Mockito.any(Subreddit.class).getId()).thenReturn(subreddit.getId());

    //     subredditService.save(subredditDto);
    //     Mockito.verify(subredditRepository, Mockito.times(1))
    //             .save(subredditArgumentCaptor.capture());

    //     Assertions.assertThat(subredditArgumentCaptor.getValue().getId()).isEqualTo(100L);
    //     Assertions.assertThat(subredditArgumentCaptor.getValue().getName())
    //             .isEqualTo("Test Subreddit");
    // }

    @Test
    @DisplayName("Should Get All Subreddits")
    public void shouldGetAllSubreddit() {
        Subreddit subreddit1 = new Subreddit(100L, "Test Subredit 1",
                "Test Subreddit 1 Description", emptyList(), Instant.now(), null);
        Subreddit subreddit2 = new Subreddit(101L, "Test Subredit 2",
                "Test Subreddit 2 Description", emptyList(), Instant.now(), null);

        SubredditDto responseDto1 =
                new SubredditDto(100L, "Test Subredit 1", "Test Subreddit 1 Description", 2);
        SubredditDto responseDto2 =
                new SubredditDto(101L, "Test Subredit 2", "Test Subreddit 2 Description", 3);

        Mockito.when(subredditMapper.mapSubredditToDto(subreddit1)).thenReturn(responseDto1);
        Mockito.when(subredditMapper.mapSubredditToDto(subreddit2)).thenReturn(responseDto2);
        Mockito.when(subredditRepository.findAll()).thenReturn(asList(subreddit1, subreddit2));

        List<SubredditDto> actualSubredditResponse = subredditService.getAll();

        Assertions.assertThat(actualSubredditResponse.get(0).getId())
                .isEqualTo(responseDto1.getId());
        Assertions.assertThat(actualSubredditResponse.get(0).getName())
                .isEqualTo(responseDto1.getName());
        Assertions.assertThat(actualSubredditResponse.get(0).getDescription())
                .isEqualTo(responseDto1.getDescription());
        Assertions.assertThat(actualSubredditResponse.get(0).getNumberOfPosts())
                .isEqualTo(responseDto1.getNumberOfPosts());

        Assertions.assertThat(actualSubredditResponse.get(1).getId())
                .isEqualTo(responseDto2.getId());
        Assertions.assertThat(actualSubredditResponse.get(1).getName())
                .isEqualTo(responseDto2.getName());
        Assertions.assertThat(actualSubredditResponse.get(1).getDescription())
                .isEqualTo(responseDto2.getDescription());
        Assertions.assertThat(actualSubredditResponse.get(1).getNumberOfPosts())
                .isEqualTo(responseDto2.getNumberOfPosts());

    }

    @Test
    @DisplayName("Should Retrieve Subreddit by Id")
    public void shouldFindSubredditById() {
        Subreddit subreddit1 = new Subreddit(100L, "Test Subredit 1",
                "Test Subreddit 1 Description", emptyList(), Instant.now(), null);

        SubredditDto responseDto1 =
                new SubredditDto(100L, "Test Subredit 1", "Test Subreddit 1 Description", 2);

        Mockito.when(subredditMapper.mapSubredditToDto(subreddit1)).thenReturn(responseDto1);
        Mockito.when(subredditRepository.findById(100L)).thenReturn(Optional.of(subreddit1));

        SubredditDto actualSubredditResponse = subredditService.getSubreddit(100L);

        Assertions.assertThat(actualSubredditResponse.getId()).isEqualTo(responseDto1.getId());
        Assertions.assertThat(actualSubredditResponse.getName()).isEqualTo(responseDto1.getName());
        Assertions.assertThat(actualSubredditResponse.getDescription())
                .isEqualTo(responseDto1.getDescription());
        Assertions.assertThat(actualSubredditResponse.getNumberOfPosts())
                .isEqualTo(responseDto1.getNumberOfPosts());
    }


}
