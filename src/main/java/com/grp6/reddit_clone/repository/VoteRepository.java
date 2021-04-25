package com.grp6.reddit_clone.repository;

import java.util.Optional;

import com.grp6.reddit_clone.model.Post;
import com.grp6.reddit_clone.model.User;
import com.grp6.reddit_clone.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
