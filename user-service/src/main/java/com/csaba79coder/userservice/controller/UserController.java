package com.csaba79coder.userservice.controller;

import com.csaba79coder.bestprotocol.api.UserApi;
import com.csaba79coder.bestprotocol.model.UserModel;
import com.csaba79coder.bestprotocol.model.UserModifyModel;
import com.csaba79coder.bestprotocol.model.UserNewModel;
import com.csaba79coder.userservice.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * responsible for the endpoints
 */
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * responsible for the endpoints
     * @return the user model
     * @param body the user model
     */
    @Override
    public ResponseEntity<UserModel> addNewUser(UserNewModel body) {
        return ResponseEntity.status(201).body(userService.addNewUser(body));
    }

    /**
     * responsible for the endpoints
     * @param userId the user id
     * @return the user modify model
     */
    @Override
    public ResponseEntity<UserModifyModel> modifyUserById(UUID userId, UserModel body) {
        return ResponseEntity.status(200).body(userService.modifyUserById(userId, body));
    }

    /**
     * responsible for the endpoints
     * @return the user model
     */
    @Override
    public ResponseEntity<List<UserModel>> renderAllUsers() {
        return ResponseEntity.status(200).body(userService.renderAllUsers());
    }

    /**
     * responsible for the endpoints
     * @param userId
     * @return the user model
     */
    @Override
    public ResponseEntity<UserModel> renderUserById(UUID userId) {
        return ResponseEntity.status(200).body(userService.renderUserById(userId));
    }
}
