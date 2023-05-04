package com.csaba79coder.userservice.util;

import com.csaba79coder.bestprotocol.model.UserModel;
import com.csaba79coder.bestprotocol.model.UserModifyModel;
import com.csaba79coder.userservice.model.entity.User;
import org.modelmapper.ModelMapper;

/**
 * responsible for mapping the entities to models
 */
public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * maps the user entity to user model
     *
     * @param user the user entity
     * @return the user model
     */
    public static UserModel mapUserEntityToModel(User user) {
        return modelMapper.map(user, UserModel.class);
    }

    /**
     * maps the user model to user entity
     *
     * @param user the user model
     * @return the user entity
     */
    public static UserModifyModel mapUserEntityToModifyModel(User user) {
        return modelMapper.map(user, UserModifyModel.class);
    }
}
