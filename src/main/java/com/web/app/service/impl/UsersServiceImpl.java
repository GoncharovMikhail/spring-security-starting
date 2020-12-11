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
 * (some of them only by authenticated users).
 *
 * @see UsersService
 */
@org.springframework.stereotype.Service
/* @PropertySource("...") is used to specify property-file, which contains necessary properties.*/
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
     * Method for checking if a user can be saved in the database(in the 'users' table).
     * As far as columns 'username' ane 'email' has the 'unique' constraints, we can't save user,
     * if he{she} provided email or username already existing in database.
     *
     * @param signUpRequest wrapper for email, username and password.
     * @return {@code true}, if user is valid for saving, else {@code false}.
     */
    private boolean isUserValidForSaving(SignUpRequestDTO signUpRequest) {
        return usersRepository
                .findByEmailAndUsername(signUpRequest.getEmail(), signUpRequest.getUsername()) == null;
    }

    /**
     * Each signed up user gets a default role (we save a {@code UsersEntity} instance),
     * but to save it correctly, we need to set this entity a role (represented as {@code RolesEntity}).
     * This method takes a default role name(defaultRoleName) and returns corresponding rolesEntity
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

    /* A default role as a string, got from a property-file (properties/service/service.properties) */
    @Value("${user.role.default}")
    private String defaultRoleName;

    //todo как пофиксить джавадок, чтоб он видел приватные поля?

    /**
     * Method saves user in database by email, username and password.
     * Other params are assigned implicitly:
     * {@link UsersEntity#id} is assigned by database:
     * {@link UsersEntity#id} is annotated with {@code @GeneratedValue(strategy = GenerationType.IDENTITY)},
     * meaning, that the persistence provider must assign primary keys for the entity using database's identity column.
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
            /* If there is the same username/password/username and password in database, we throw an Exception. */
            throw new UserAlreadyExistsException(
                    "User, having username: '"
                            + username
                            + "' or email: '" +
                            email
                            + "' already exists"
            );
        }
    }

    @Override
    public UsersEntity loadUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public void enableOrDisableUser(boolean toEnable, String username) {
        usersRepository.enableOrDisableUser(toEnable, username);
    }
}
