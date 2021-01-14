package com.web.app.security;

import com.web.app.config.SpringSecurityConfig;
import com.web.app.entity.UsersEntity;
import com.web.app.security.util.UsersDetailsUtils;
import com.web.app.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link UserDetailsService}.
 * <p>
 * The {@link UserDetailsService} contains only one method - {@link UserDetailsService#loadUserByUsername(String)}
 * to be implemented. As far as we store all user's data in the database, we should load them from that database.
 * Particularly, in this method we should load user from the database by username.
 * <p>
 * Whenever user logs in by providing <pre> username </pre> and a <pre> password </pre>,
 * {@code Spring Security} loads this user(by specified username) to server, then maps it to some of
 * {@code UserDetails} implementing class, and only then checks (somewhere under the hood)
 * if entered password and the one from database matches.
 *
 * @see SpringSecurityConfig#passwordEncoder() documentation - there are some additional details there.
 */
@Service
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class UsersDetailsService implements UserDetailsService {

    /**
     * A service, which will load user from the database.
     *
     * @see UsersService#loadUserByUsername(String)
     */
    private final UsersService usersService;

    //todo мне кажется слишком неформально и заумно

    /** Autowire this service.
     * <p>
     * An instance of a class, annotated with
     * <ul>
     *     <li>
     *         {@link Service},
     *     </li>
     *     <li>
     *         {@link org.springframework.stereotype.Repository},
     *     </li>
     * </ul>
     * or any other annotation,
     * which contains a {@link org.springframework.stereotype.Component} inside itself,
     * will be created and stored in so-called "IOC-container", meaning,
     * {@code Spring} will create an instance of a class,
     * configuring it how by the way (for example, inject another component
     * or inject a field value with the {@link org.springframework.context.annotation.PropertySource} and
     * {@link org.springframework.beans.factory.annotation.Value} annotations) -
     * {@link org.springframework.beans.factory.config.BeanPostProcessor} magic going under the hood
     * (well, not quite magic. If you want to learn more about {@code Spring} internals - better
     * watch some of <i>Eugeniy Borisov's</i> talks on youtube).
     * <strong>only once</strong>,
     * i.e., that instance will be a <strong>singleton</strong> by default,
     * but we can configure this if needed.
     * <p>
     * {@link Autowired} above a method(a constructor as well) means that all method's parameters
     * will be injected from the "IOC-container"
     * <p>
     * <strong>NOTE:</strong> we can also {@code Autowire} components by putting {@link Autowired} above
     * the field we want to {@code Autowire}, but constructor injection is preferred.
     */
    @Autowired
    public UsersDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* Try to find user with a specified username in the database. */
        UsersEntity usersEntity = usersService.loadUserByUsername(username);

        /* Checking, if a user, having specified name, exists.
         * At this stage we don't care if users is banned or not.
         * Just check if he/she exists. */
        if (usersEntity == null) {
            log.info("IN " + this.getClass() +
                    "loadUserByUsername(String username) - user, having username '{}' doesn't exists", username);

            throw new UsernameNotFoundException("IN " + this.getClass() +
                    "loadUserByUsername(String username) - user, having username '" + username + "'doesn't exists");
        }
        log.info("IN CustomUserDetailsService.class, loadUserByUsername(String username) - user, having userName: " +
                "'{}' successfully loaded", username);

        /* Map usersEntity instance to user details, before returning it, hence the return-type of this method is some
         * implementation of the UserDetails interface. Overall, spring security works with UserDetails. */
        return UsersDetailsUtils.mapEntityToUsersDetails(usersEntity);
    }
}
