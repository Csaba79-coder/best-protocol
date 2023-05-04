package com.csaba79coder.userservice.model.entity;

import com.csaba79coder.userservice.model.base.entity.Auditable;
import com.csaba79coder.userservice.model.value.Availability;
import com.csaba79coder.userservice.model.value.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

/**
 * This class represents a user entity that can be persisted to a database
 * using JPA.
 */
@Entity
/**
 * This annotation specifies the name of the database table to which
 * this entity is mapped. In this case, the table name is "bp_user".
 */
@Table(name = "bp_user")
/**
 * This annotation indicates that this class can be embedded in other
 * entities, rather than being treated as a standalone entity itself.
 */
@Embeddable
/**
 * This annotation indicates that this class has getter methods generated
 * for all non-static fields. Getter methods are used to retrieve the value
 * of an object's properties.
 */
@Getter
/**
 * This annotation indicates that this class has setter methods generated
 * for all non-final non-static fields. Setter methods are used to set the
 * value of an object's properties.
 */
@Setter
/**
 * This annotation generates a no-argument constructor for this class.
 * The constructor initializes all fields to their default values.
 */
@NoArgsConstructor
/**
 * This annotation generates a constructor for this class that takes
 * arguments for all non-static fields. The constructor initializes each
 * field with the corresponding argument.
 */
@AllArgsConstructor
/**
 * This annotation specifies a SQL WHERE clause that is added to all
 * SELECT statements generated for this entity. In this case, the clause
 * ensures that only users whose "availability" field is not equal to
 * "DELETED" are returned.
 */
@Where(clause = "availability != 'DELETED'")
public class User extends Auditable {

    /**
     * This field represents the username of this user.
     */
    @Column(name = "user_name")
    private String username;

    /**
     * This field represents the email address of this user.
     * It is annotated with @Column(name = "email", unique = true), which tells Hibernate to use the "email" column
     * in the database to store the value of this field. It is also annotated with @Column(unique = true), which
     * tells Hibernate to add a unique constraint to the "email" column. This means that the value of this field
     * must be unique in the database.
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * This field represents the password of this user.
     * It is annotated with @JsonProperty(access = JsonProperty.Access.WRITE_ONLY), which tells Jackson to only
     * serialize this field, but not deserialize it. This means that when a user is created or updated, the password
     * field will not be included in the request body. This is because we don't want to store the password in the
     * database as plain text. Instead, we will use the BCryptPasswordEncoder to encode the password before storing it
     * in the database, also the model has no such property as password, it is only used for validation.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    /**
     * This field represents the availability status of this user.
     * It is an enumeration value of type "Availability", which can be
     * one of several possible values: AVAILABLE, UNAVAILABLE, or DELETED.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability = Availability.AVAILABLE;

    /**
     * This field represents the role of the user within the system. It is
     * annotated with @Enumerated(EnumType.STRING), which tells Hibernate
     * to store the value of the enum in the database as a string. It is
     * also annotated with @Column(name = "user_role"), which tells
     * Hibernate to use the "user_role" column in the database to store
     * this value. Finally, it is initialized with the default value of
     * Role.USER, which represents a regular user.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role = Role.USER;
}
