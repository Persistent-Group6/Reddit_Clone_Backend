package com.grp6.reddit_clone.service;

import java.util.List;

import javax.transaction.Transactional;

import com.grp6.reddit_clone.dto.UserDto;
import com.grp6.reddit_clone.exceptions.SpringRedditException;
import com.grp6.reddit_clone.mapper.UserMapper;
import com.grp6.reddit_clone.model.User;
import com.grp6.reddit_clone.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::mapUserToDto).collect(toList());
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("User with ID: " + id + " not found."));

        return userMapper.mapUserToDto(user);
    }
}
