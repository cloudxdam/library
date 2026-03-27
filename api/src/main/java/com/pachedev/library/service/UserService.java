package com.pachedev.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pachedev.library.dto.user.CreateUserRequest;
import com.pachedev.library.dto.user.ReplaceUserRequest;
import com.pachedev.library.dto.user.UpdateUserRequest;
import com.pachedev.library.dto.user.UserResponse;
import com.pachedev.library.exception.BusinessRuleException;
import com.pachedev.library.exception.DuplicateResourceException;
import com.pachedev.library.exception.ResourceNotFoundException;
import com.pachedev.library.mapper.UserMapper;
import com.pachedev.library.model.User;
import com.pachedev.library.repository.LoanRepository;
import com.pachedev.library.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, LoanRepository loanRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.userMapper = userMapper;
    }

    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with email " + request.email() + " already exists");
        }
        User newUser = userMapper.toEntity(request);
        User savedUser = userRepository.save(newUser);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse findById(Long id) {
        User user = findUserEntityById(id);
        return userMapper.toResponse(user);
    }

    private User findUserEntityById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + id));
        return user;
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : users) {
            responseList.add(userMapper.toResponse(user));
        }

        return responseList;
    }

    public UserResponse update(Long id, ReplaceUserRequest request) {
        User existingUser = findUserEntityById(id);

        validateEmailForUpdate(request.email(), existingUser);

        userMapper.replaceEntityFromRequest(request, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    private void validateEmailForUpdate(String newEmail, User existingUser) {
        if (!newEmail.equals(existingUser.getEmail())
                && userRepository.existsByEmail(newEmail)) {
            throw new DuplicateResourceException("A user with email " + newEmail + " already exists");
        }
    }

    public UserResponse patchUpdate(Long id, UpdateUserRequest request) {
        User existingUser = findUserEntityById(id);

        if (request.email() != null) {
            validateEmailForUpdate(request.email(), existingUser);
        }

        userMapper.updateEntityFromRequest(request, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    public void delete(Long id) {
        User existingUser = findUserEntityById(id);

        if (loanRepository.existsByUserIdAndReturnDateIsNull(id)) {
            throw new BusinessRuleException("Cannot delete user with active loans");
        }
        userRepository.delete(existingUser);
    }
}
