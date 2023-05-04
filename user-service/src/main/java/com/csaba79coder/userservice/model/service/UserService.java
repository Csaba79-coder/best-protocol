package com.csaba79coder.userservice.model.service;

import com.csaba79coder.bestprotocol.model.UserModel;
import com.csaba79coder.bestprotocol.model.UserModifyModel;
import com.csaba79coder.bestprotocol.model.UserNewModel;
import com.csaba79coder.userservice.model.entity.User;
import com.csaba79coder.userservice.model.persistence.UserRepository;
import com.csaba79coder.userservice.model.value.Availability;
import com.csaba79coder.userservice.model.value.Role;
import com.csaba79coder.userservice.util.Mapper;
import com.csaba79coder.userservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * responsible for the business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**

     Retrieves all users from the database and maps them to a list of user models.
     @return a list of user models representing all users in the database
     */
    public List<UserModel> renderAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(Mapper::mapUserEntityToModel)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new user to the database if the email doesn't already exist,
     * and if the email and password meet certain validation criteria.
     * The new user is represented by a UserNewModel object.
     *
     * @param userNewModel the UserNewModel object representing the new user
     * @return a UserModel object representing the newly added user
     * @throws IllegalArgumentException if the email already exists, or if the
     * email or password do not meet the validation criteria
     */
    public UserModel addNewUser(UserNewModel userNewModel) {
        if (userRepository.findUserByEmail(userNewModel.getEmail()).isPresent()) {
            String message = "Email already exists";
            log.error(message);
            throw new IllegalArgumentException(message);
        } else {
            if (ValidationUtil.isValidEmail(userNewModel.getEmail()) && ValidationUtil.isValidPassword(userNewModel.getPassword())) {
                if (userNewModel.getPassword().equals(userNewModel.getRepeatPassword())) {
                    User user = new User();
                    user.setEmail(userNewModel.getEmail());
                    user.setUsername(userNewModel.getUsername());
                    user.setPassword(new BCryptPasswordEncoder().encode(userNewModel.getPassword()));
                    return Mapper.mapUserEntityToModel(userRepository.save(user));
                } else {
                    String message = "Passwords do not match";
                    log.error(message);
                    throw new IllegalArgumentException(message);
                }
            } else {
                String message = "Invalid email or password";
                log.error(message);
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * @param userId
     * @return a UserModel object from a User entity
     */
    public UserModel renderUserById(UUID userId) {
        return Mapper.mapUserEntityToModel(userRepository.findUserById(userId).orElseThrow(() -> {
            String message = "User not found";
            log.error(message);
            throw new IllegalArgumentException(message);
        }));
    }

    /**
     * @param userId
     * @return a UserModel object from a modified User entity
     */
    public UserModifyModel modifyUserById(UUID userId, UserModel body) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> {
            String message = "User not found";
            log.error(message);
            throw new IllegalArgumentException(message);
        });
        if (body.getEmail() != null) {
            user.setEmail(body.getEmail());
        }

        if (body.getUsername() != null) {
            user.setUsername(body.getUsername());
        }

        if (body.getAvailability() != null) {
            user.setAvailability(Availability.valueOf(body.getAvailability().name()));
        }

        if (body.getRole() != null) {
            user.setRole(Role.valueOf(body.getRole().name()));
        }

        user.setUpdatedAt(LocalDateTime.now());
        return Mapper.mapUserEntityToModifyModel(userRepository.save(user));
    }
}
