package com.grp6.reddit_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grp6.reddit_clone.model.Post;
import com.grp6.reddit_clone.model.Subreddit;
import com.grp6.reddit_clone.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}