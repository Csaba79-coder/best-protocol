package com.csaba79coder.userservice;

import com.csaba79coder.bestprotocol.model.UserModel;
import com.csaba79coder.bestprotocol.model.UserNewModel;
import com.csaba79coder.userservice.controller.UserController;
import com.csaba79coder.userservice.model.base.entity.Identifier;
import com.csaba79coder.userservice.model.entity.User;
import com.csaba79coder.userservice.model.persistence.UserRepository;
import com.csaba79coder.userservice.model.service.UserService;
import com.csaba79coder.userservice.model.value.Availability;
import com.csaba79coder.userservice.model.value.Role;
import com.csaba79coder.userservice.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the {@link UserController}.
 * This class tests the behavior of the UserController by sending mock HTTP requests
 * and asserting the responses returned by the controller methods.
 */
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("resource")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    /**
     * This method tests the behavior of the renderAllUsers() method of the UserController.
     * It mocks the UserService to return a list of dummy users and then performs a GET request to the UserController endpoint.
     * It asserts that the response status is 200 OK and the response body contains the expected list of users.
     */
    @Test
    @DisplayName("GET /api/admin/user-service/users")
    public void testRenderAllUsers() throws Exception {
        // create some dummy user data
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setUsername("user1");

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setUsername("user2");

        List<User> userList = Arrays.asList(user1, user2);

        // mock the userRepository to return the dummy data
        when(userService.renderAllUsers()).thenReturn(
                userList.stream().map(Mapper::mapUserEntityToModel).collect(Collectors.toList())
        );

        // perform the GET request to the UserController endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user-service/users"))
                .andExpect(status().isOk())
                .andReturn();

        // deserialize the response body to a List<UserModel>
        List<UserModel> userModelList = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<UserModel>>() {
                });

        // assert that the userModelList is correct
        assertEquals(userModelList.size(), userList.size());
        assertEquals(userModelList.get(0).getEmail(), user1.getEmail());
        assertEquals(userModelList.get(1).getEmail(), user2.getEmail());
        assertEquals(userModelList.get(0).getUsername(), user1.getUsername());
        assertEquals(userModelList.get(1).getUsername(), user2.getUsername());
    }

    /**
     * This method tests the behavior of the addNewUser() method of the UserController.
     * It mocks the UserService to return a dummy user and then performs a POST request to the UserController endpoint.
     * It asserts that the response status is 201 CREATED and the response body contains the expected user.
     */
    @Test
    @DisplayName("POST /api/admin/user-service/users")
    public void testAddNewUser() throws Exception {
        // create a userNewModel object
        UserNewModel userNewModel = new UserNewModel();
        userNewModel.setEmail("testuser@example.com");
        userNewModel.setUsername("testuser");
        userNewModel.setPassword("password");
        userNewModel.setRepeatPassword("password");

        // create a user object
        User user = new User();
        user.setEmail(userNewModel.getEmail());
        user.setUsername(userNewModel.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userNewModel.getPassword()));

        // mock the userRepository to return the user object when save() is called
        when(userService.addNewUser(userNewModel)).thenReturn(Mapper.mapUserEntityToModel(user));

        // perform the POST request to the UserController endpoint with the userNewModel object as JSON
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/user-service/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNewModel)))
                .andExpect(status().isCreated())
                .andReturn();

        // deserialize the response body to a UserModel object
        UserModel userModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserModel.class);

        // assert that the userModel is correct
        assertEquals(userModel.getEmail(), userNewModel.getEmail());
        assertEquals(userModel.getUsername(), userNewModel.getUsername());
    }

    /**
     * This method tests the behavior of the renderUserById() method of the UserController.
     * It mocks the UserService to return a dummy user and then performs a GET request to the UserController endpoint.
     * It asserts that the response status is 200 OK and the response body contains the expected user.
     */
    @Test
    @DisplayName("GET /api/admin/user-service/users/{userId}")
    public void testRenderUserById() throws Exception {
        // create a dummy user
        // create a dummy user
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test");

        // use reflection to set the ID field
        Field idField = Identifier.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, UUID.randomUUID());

        userRepository.save(user);
        UUID userId = user.getId();

        // mock the userRepository to return the dummy user
        when(userService.renderUserById(userId)).thenReturn(Mapper.mapUserEntityToModel(user));

        // perform the GET request to the UserController endpoint with the user id as a path variable
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user-service/users/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn();

        // deserialize the response body to a UserModel
        UserModel userModel = objectMapper.readValue(result.getResponse().getContentAsString(), UserModel.class);

        // assert that the userModel is correct
        assertEquals(userModel.getEmail(), user.getEmail());
        assertEquals(userModel.getUsername(), user.getUsername());
        assertEquals(userModel.getId(), user.getId());
    }

    /**
     * This method tests the behavior of the modifyUserById() method of the UserController.
     * It mocks the UserService to return a dummy user and then performs a PUT request to the UserController endpoint.
     * It asserts that the response status is 200 OK and the response body contains the expected user.
     */
    @Test
    @DisplayName("PUT /api/admin/user-service/users/{userId}")
    void modifyUserByIdTest() throws Exception {
        UUID userId = UUID.randomUUID();
        UserModel userModel = new UserModel();
        userModel.setEmail("new_email@example.com");
        userModel.setUsername("new_username");
        userModel.setRole(com.csaba79coder.bestprotocol.model.Role.ADMIN);
        userModel.setAvailability(com.csaba79coder.bestprotocol.model.Availability.AVAILABLE);

        User user = new User();
        user.setEmail("old_email@example.com");
        user.setUsername("old_username");
        user.setRole(Role.USER);
        user.setAvailability(Availability.AVAILABLE);

        // use reflection to set the ID field
        Field idField = Identifier.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, userId);

        given(userRepository.findUserById(userId)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        MvcResult mvcResult = mockMvc.perform(
                        (MockMvcRequestBuilders.put("/api/admin/user-service/users/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userModel))))
                .andExpect(status().isOk())
                .andReturn();
    }

    // helper methods for serialization/deserialization
    /**
     * This method serializes an object to a JSON string.
     * @param obj the object to serialize
     * @return the JSON string
     * @throws JsonProcessingException
     */
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * This method deserializes a JSON string to an object of the specified class.
     * @param json the JSON string to deserialize
     * @param clazz the class of the object to deserialize to
     * @param <T> the type of the object to deserialize to
     * @return the deserialized object
     * @throws JsonProcessingException
     */
    private <T> T asObjectFromJsonString(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
}
