package com.grp6.reddit_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grp6.reddit_clone.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    static Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null	;
	}
}