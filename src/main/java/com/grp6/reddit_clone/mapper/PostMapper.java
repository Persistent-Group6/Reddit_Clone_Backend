package com.grp6.reddit_clone.mapper;

import java.util.Optional;

import com.grp6.reddit_clone.dto.PostRequest;
import com.grp6.reddit_clone.dto.PostResponse;
import com.grp6.reddit_clone.model.Post;
import com.grp6.reddit_clone.model.Subreddit;
import com.grp6.reddit_clone.model.User;
import com.grp6.reddit_clone.model.Vote;
import com.grp6.reddit_clone.model.VoteType;
import com.grp6.reddit_clone.service.AuthService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    public abstract PostResponse mapToDto(Post post);

}
