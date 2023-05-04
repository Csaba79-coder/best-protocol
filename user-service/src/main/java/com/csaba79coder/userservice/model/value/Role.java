package com.csaba79coder.userservice.model.value;

/**
 * This enum represents the different roles that a user can have within
 * the system. It contains three possible values: USER, ADMIN, and
 * SUPER_ADMIN.
 */
public enum Role {

    /**
     * The USER role represents a regular user of the system.
     */
    USER,

    /**
     * The ADMIN role represents an administrator of the system.
     */
    ADMIN,

    /**
     * The SUPER_ADMIN role represents a super administrator of the
     * system, with the highest level of privileges.
     */
    SUPER_ADMIN
}
