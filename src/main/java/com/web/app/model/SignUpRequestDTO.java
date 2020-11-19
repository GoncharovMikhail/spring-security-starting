package com.web.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A wrapper class for to store all sign up request data - user signs up by specifying:
 * <ul>
 *     <li>
 *         email,
 *     </li>
 *     <li>
 *         username,
 *     </li>
 *     <li>
 *         password.
 *     </li>
 * </ul>
 *
 * @see com.web.app.rest.CommonTemplatesController#registration(SignUpRequestDTO)
 */
/* Generates an empty constructor. It is used to deserialize a JSON. */
@NoArgsConstructor
/* Generates a constructor for all fields of a class. */
@AllArgsConstructor
/* Generates getters for all fields of a class. */
@Getter
/* Generates setters for all fields of a class. */
@Setter
public class SignUpRequestDTO {

    private String email;

    private String username;

    private String password;
}
