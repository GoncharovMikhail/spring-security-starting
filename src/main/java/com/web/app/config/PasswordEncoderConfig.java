package com.web.app.config;

import com.web.app.model.SignUpRequestDTO;
import com.web.app.security.UsersDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A configuration class, storing password encoder.
 */
@Configuration
/* @PropertySource("...") gets a relative path (to the "resources" folder)
 * to a property-file as a parameter to read properties from.
 *
 * In combination with @Value("${...}"), declared above a field,
 * we can inject a value into the annotated field, which was read
 * from the specified property-file. */
@PropertySource("properties/security/passwordencoder.properties")
public class PasswordEncoderConfig {

    /* Value("${...}") gets a property's name as a parameter, then finds it in the property-file
     * (specified in @PropertySource("...") annotation), and sets it to the annotated field.
     * If there is no property, having specified name, then nothing will be injected in the field.
     * In this case, we inject number of rounds of encoding(the log rounds to use, between 4 and 31).
     *
     * NOTE: write name of a property  in ${...} - otherwise, property won't be injected to the field. */
    @Value("${password.encoder.strength}")
    private Integer passwordEncoderStrength;

    /**
     * A {@link PasswordEncoder} bean to use.
     * <p>
     * {@code PasswordEncoder} is used to encode user's password before saving it in the database
     * ({@link com.web.app.service.impl.UsersServiceImpl#saveUserInDatabase(SignUpRequestDTO)}) or
     * in-memory saving or any other type of persisting user's password.
     * <p>
     * Also, it is used internally by Spring security:
     * whenever user tries to log in, he/she specifies username and password.
     * Then, a POST request, containing specified username and password is sent to the server.
     * Spring security loads user's security data
     * (represented as {@link org.springframework.security.core.userdetails.UserDetails})
     * by specified username.
     * <p>
     * Its necessary to implement {@link org.springframework.security.core.userdetails.UserDetailsService}
     * interface, particularly, the
     * {@link org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(String)}
     * method. As I mentioned before, user's data may be stored in a several ways(in the database,
     * in-memory, e.t.c), so loading a user by username can be proceeded in a several different ways
     * as well.
     * <p>
     * If there is no user with the specified username,
     * the {@link org.springframework.security.core.userdetails.UsernameNotFoundException} is thrown.
     * Otherwise, Spring security loads {@code UserDetails}, which is <strong>never null</strong>.
     * After that, the password from the POST request get encoded, and then, Spring security checks,
     * if passwords, stored in loaded {@code UserDetails} and the one from POST request encoded as well,
     * matches.
     * <p>
     * The idea of using {@code PasswordEncoder} makes sense:
     * if the attacker hacked our database and tries to login -
     * he inputs someone's username and <strong>encoded</strong> password.
     * Then, these username and password goes to server,
     * inputted (already encoded password, hence he took it from my database), gets encoded one more
     * time and it is almost impossible that the real password and the one, entered by attacker, will
     * coincide.
     * <p>
     *
     * <strong>NOTE:</strong> any encoded password <strong>never</strong> gets decoded.
     * <p>
     *
     * <strong>NOTE:</strong> if two passwords matches, this <strong>doesn't</strong> mean they are equal.
     * Encoding and matching algorithms are complicated.
     * For example, if you encode one string for a few times, you may get different hashed ones.
     * You may try this out here:
     * https://bcrypt-generator.com/
     * Try to encode something for a few times!
     * See documentation for {@link BCryptPasswordEncoder} here:
     * http://docs.spring.io/spring-security/site/docs/3.2.5.RELEASE/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
     * <p>
     *
     * @see com.web.app.security.UsersDetailsService - my implementation of the {@code UserDetailsService} interface.
     * @see UsersDetails - my implementation of the {@code UserDetails} interface.
     * @see BCryptPasswordEncoder#BCryptPasswordEncoder(int) for more info about the "strength" parameter.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordEncoderStrength);
    }
}
