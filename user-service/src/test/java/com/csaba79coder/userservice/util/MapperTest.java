package com.csaba79coder.userservice.util;

import com.csaba79coder.bestprotocol.model.UserModel;
import com.csaba79coder.bestprotocol.model.UserModifyModel;
import com.csaba79coder.userservice.model.entity.User;
import com.csaba79coder.userservice.model.value.Availability;
import com.csaba79coder.userservice.model.value.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * responsible for testing the mapper
 */
class MapperTest {

    /**
     * tests the mapUserEntityToModel method
     */
    @Test
    public void testMapUserEntityToModel() {
        User user = new User();
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");
        user.setAvailability(Availability.AVAILABLE);
        user.setRole(Role.USER);

        UserModel userModel = Mapper.mapUserEntityToModel(user);

        assertEquals(user.getId(), userModel.getId());
        assertEquals(user.getUsername(), userModel.getUsername());
        assertEquals(user.getEmail(), userModel.getEmail());
        assertEquals(user.getAvailability().name(), userModel.getAvailability().name());
        assertEquals(user.getRole().name(), userModel.getRole().name());
    }

    /**
     * tests the mapUserEntityToModifyModel method
     */
    @Test
    public void testMapUserEntityToModifyModel() {
        User user = new User();
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");
        user.setAvailability(Availability.AVAILABLE);
        user.setRole(Role.USER);

        UserModifyModel userModifyModel = Mapper.mapUserEntityToModifyModel(user);

        assertEquals(user.getUsername(), userModifyModel.getUsername());
        assertEquals(user.getEmail(), userModifyModel.getEmail());
        assertEquals(user.getAvailability().name(), userModifyModel.getAvailability().name());
        assertEquals(user.getRole().name(), userModifyModel.getRole().name());
    }
}