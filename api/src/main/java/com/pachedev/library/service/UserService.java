package com.pachedev.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pachedev.library.model.User;
import com.pachedev.library.repository.LoanRepository;
import com.pachedev.library.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public User create(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("A user with email " + newUser.getEmail() + " already exists");
        }
        return userRepository.save(newUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found user with Id: " + id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long id, User updatedUser) {
        User existingUser = findById(id);

        validateEmailForUpdate(updatedUser.getEmail(), existingUser);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    private void validateEmailForUpdate(String newEmail, User existingUser) {
        if (!newEmail.equals(existingUser.getEmail())
                && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("A user with email " + newEmail + " already exists");
        }
    }

    public User patchUser(Long id, Map<String, Object> updates) {
        User existingUser = findById(id);

        if (updates.containsKey("name")) {
            existingUser.setName((String) updates.get("name"));
        }

        if (updates.containsKey("email")) {
            String newEmail = (String) updates.get("email");
            validateEmailForUpdate(newEmail, existingUser);
            existingUser.setEmail(newEmail);
        }

        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        User existingUser = findById(id);

        if (loanRepository.existsByUserIdAndReturnDateIsNull(id)) {
            throw new IllegalArgumentException("Cannot delete user with active loans");
        }
        userRepository.delete(existingUser);
    }
}
