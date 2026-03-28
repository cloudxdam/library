package com.pachedev.library.mapper;

import org.springframework.stereotype.Component;

import com.pachedev.library.dto.user.CreateUserRequest;
import com.pachedev.library.dto.user.ReplaceUserRequest;
import com.pachedev.library.dto.user.UpdateUserRequest;
import com.pachedev.library.dto.user.UserResponse;
import com.pachedev.library.model.User;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());

        return user;
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public void updateEntityFromRequest(UpdateUserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        if (request.name() != null) {
            user.setName(request.name());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }
    }

    public void replaceEntityFromRequest(ReplaceUserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        user.setName(request.name());
        user.setEmail(request.email());
    }
}
