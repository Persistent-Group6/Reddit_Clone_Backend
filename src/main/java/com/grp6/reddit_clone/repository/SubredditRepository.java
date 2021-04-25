package com.grp6.reddit_clone.repository;

import java.util.Optional;

import com.grp6.reddit_clone.model.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByName(String subredditName);
}
