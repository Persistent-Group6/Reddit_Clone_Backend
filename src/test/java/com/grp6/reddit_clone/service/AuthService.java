package com.grp6.reddit_clone.service;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grp6.reddit_clone.model.User;
import com.grp6.reddit_clone.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
public class AuthService {
	
	 @Transactional(readOnly = true)
	    public User getCurrentUser() {
	        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
	                getContext().getAuthentication().getPrincipal();
	        return UserRepository.findByUsername(principal.getUsername())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	    }

}
