package com.web.app.service.impl;

import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.repository.RolesRepository;
import com.web.app.repository.UsersRepository;
import com.web.app.service.UsersService;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link UsersService} implementation. Basically, all methods of this service can be invoked by all users
 * (some of them only by authenticated ones).
 *
 * @see UsersService
 */
@org.springframework.stereotype.Service
/* The <pre> @PropertySource("...") </pre> gets a relative path (to the <pre> "resources" </pre> folder)
 * to a property-file to read properties from it.
 *
 * In combination with <pre> @Value("${...}") </pre>, declared above a field,
 * we can inject a value into the annotated field, which was read
 * from the specified property-file.
 * <strong>Note:</strong>: write the name of a property (in the <pre> @Value("...") </pre> annotation)
 * in <pre> ${...} </pre> - otherwise, property won't be injected to the annotated field. */
@PropertySource("properties/service/service.properties")
/* @Slf4j is used for logging */
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            RolesRepository rolesRepository,
                            PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method for checking if a user can be saved in the database(in the "users" table).
     * As far as columns "username" ane "email" has the {@code unique} constraint,
     * we can't save user, if he/she provided email or username, already existing in database.
     *
     * @param signUpRequest wrapper for email, username and password.
     * @return {@code true}, if user is valid for saving, else {@code false}.
     */
    private boolean isUserValidForSaving(SignUpRequestDTO signUpRequest) {
        return usersRepository
                .findByEmailAndUsername(signUpRequest.getEmail(), signUpRequest.getUsername()) == null;
    }

    /**
     * Each signed up user gets a default role (we save a {@link UsersEntity} instance),
     * but to save it correctly, we need to set this {@code entity} a role (represented as {@code RolesEntity}).
     * This method takes a default role name(defaultRoleName) and returns corresponding {@code RolesEntity} instance.
     *
     * @return a Set<RolesEntity>, containing default role.
     * @see UsersEntity#setRoles(Set rolesEntities) - note, that this method takes a Set<RolesEntity> as a parametr
     */
    private Set<RolesEntity> getDefaultUserRole() {
        RolesEntity rolesEntity = rolesRepository.findByRole(defaultRoleName);

        Set<RolesEntity> defaultUserRoles = new HashSet<>();
        defaultUserRoles.add(rolesEntity);

        return defaultUserRoles;
    }

    /* A default role (as a string), got from a property-file (classpath:properties/service/service.properties). */
    @Value("${user.role.default}")
    private String defaultRoleName;

    /**
     * Method saves user in database by email, username and password.
     * Other params are assigned implicitly:
     * {@link UsersEntity#id} is assigned by database:
     * {@link UsersEntity#id} is annotated with {@code @GeneratedValue(strategy = GenerationType.IDENTITY)},
     * meaning, that the persistence provider must assign primary keys for the {@code entity} using database's identity column.
     * {@link UsersEntity#created} and {@link UsersEntity#updated} are assigned for a real-time date.
     * {@link UsersEntity#enabled} assigned to {@code true}.
     * {@link UsersEntity#email} and {@link UsersEntity#username} are assigned simply.
     * {@link UsersEntity#password} gets encoded before saving in database.
     * {@link UsersEntity#roles} assigned implicitly to a default role.
     * {@link UsersEntity#agendas} assigned to an empty set.
     *
     * @see UsersService#saveUserInDatabase(SignUpRequestDTO) for some additional details.
     */
    @Override
    public void saveUserInDatabase(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException {
        String email = signUpRequest.getEmail();
        String username = signUpRequest.getUsername();

        if (isUserValidForSaving(signUpRequest)) {
            UsersEntity userToSave = new UsersEntity();

            userToSave.setCreated(new Date());
            userToSave.setUpdated(new Date());
            userToSave.setEnabled(true);
            userToSave.setEmail(email);
            userToSave.setUsername(username);
            userToSave.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userToSave.setRoles(getDefaultUserRole());
            userToSave.setAgendas(new HashSet<>());

            usersRepository.save(userToSave);

            log.info("IN " + this.getClass()
                            + ", "
                            + "saveUserInDatabase(SignUpRequestDTO signUpRequest)"
                            + " - user {} successfully saved",
                    username);
        } else {
            log.info(
                    "IN " + this.getClass()
                            + ", "
                            + "saveUserInDatabase(SignUpRequestDTO signUpRequest)"
                            + " - user '{}' couldn't be saved",
                    username
            );
            /* If there are the same email/username in the database,
             * we throw an <pre> Exception. </pre> */
            throw new UserAlreadyExistsException(
                    "User, having username: '"
                            + username
                            + "' or email: '" +
                            email
                            + "' already exists"
            );
        }
    }

    /**
     * This method loads user by specified username.
     *
     * @param username the specified username.
     * @return a {@link UsersEntity} instance.
     * @see UsersRepository#findByUsername(String)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(String),
     * and my implementation of this interface:
     * {@link com.web.app.security.UsersDetailsService#loadUserByUsername(String)}
     */
    @Override
    public UsersEntity loadUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    /**
     * This method enables or disables a user, depending on the 'toEnable' parameter's value.
     * @param toEnable a specified parameter.
     * @param username username.
     * @see UsersRepository#enableOrDisableUser(boolean, String)
     */
    @Override
    public void enableOrDisableUser(boolean toEnable, String username) {
        usersRepository.enableOrDisableUser(toEnable, username);
    }
}
