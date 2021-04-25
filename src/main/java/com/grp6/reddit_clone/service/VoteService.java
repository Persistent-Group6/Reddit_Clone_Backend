package com.grp6.reddit_clone.service;

import static com.grp6.reddit_clone.model.VoteType.UPVOTE;

import java.util.Optional;

import com.grp6.reddit_clone.dto.VoteDto;
import com.grp6.reddit_clone.exceptions.PostNotFoundException;
import com.grp6.reddit_clone.exceptions.SpringRedditException;
import com.grp6.reddit_clone.model.Post;
import com.grp6.reddit_clone.model.Vote;
import com.grp6.reddit_clone.repository.PostRepository;
import com.grp6.reddit_clone.repository.VoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
    }
}