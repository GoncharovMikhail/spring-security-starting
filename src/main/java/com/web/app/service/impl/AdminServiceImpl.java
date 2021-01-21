package com.web.app.service.impl;

import com.web.app.entity.UsersEntity;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.service.AdminService;
import com.web.app.service.UsersService;
import com.web.app.exceptions.CantBanAdminException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service for <strong>admins</strong>. All methods in this class can be executed <strong>only</strong> by admins.
 *
 * @see com.web.app.service.AdminService
 */
@Service
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class AdminServiceImpl implements AdminService {

    /**
     * A service,which is needed to execute admin's requests.
     */
    private final UsersService usersService;

    /**
     * Autowire this service.
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
     * which contains a {@link org.springframework.stereotype.Component} inside itself, or even beans from class, annotated with {@link org.springframework.stereotype.Component}
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
     * but I can configure this if needed.
     * <p>
     * {@link Autowired} above a method(a constructor as well) means that all method's parameters
     * will be injected from the "IOC-container".
     * <p>
     * <strong>NOTE:</strong> I can also {@code Autowire} components by putting {@link Autowired} above
     * the field I want to {@code Autowire}, but constructor injection is preferred.
     * <p>
     * <strong>NOTE:</strong> prefer inject by interface, not an implementation-class.
     */
    @Autowired
    public AdminServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Method for banning user by specified username.
     *
     * @see AdminService#banUserByUsername(String) for additional details.
     */
    @Override
    public void banUserByUsername(String username) throws WrongUsernameException, CantBanAdminException {
        /* Get user that is going to be banned. */
        UsersEntity userToBan = usersService.loadUserByUsername(username);

        /* Admin entered wrong username. */
        if (userToBan == null) {
            log.info("There is no user, having username: '{}'", username);
            throw new WrongUsernameException("There is no user, having username: " + username);
        }

        /* Admins can't ban each other. */
        boolean isUserToBanAnAdmin = userToBan
                .getRoles().stream()
                .anyMatch(rolesEntity -> rolesEntity.getRole().equals("ROLE_ADMIN"));
        if (isUserToBanAnAdmin) {
            log.info("IN " + this.getClass()
                            + ", banUserByUsername(String username) method"
                            + " - user '{}' is an admin - unable to ban",
                    username);
            throw new CantBanAdminException("Can't ban user, having username: '" + username +
                    "' - he/she is an admin!");
        }

        /* User, having specified name, was banned before. */
        if (!userToBan.isEnabled()) {
            log.info("IN " + this.getClass()
                            + ", banUserByUsername(String username) method"
                            + " - user '{}' was already banned",
                    username);
            /* Nothing to do then - just exit. */
            return;
        }
        /* Now I are sure, that user, having specified username, exists and he{she} wasn't banned before,
         * then, I just ban this user. */
        usersService.enableOrDisableUser(false, username);

        log.info("IN " + this.getClass()
                        + ", banUserByUsername(String username) method"
                        + " - user '{}' was banned",
                username);
    }

    /**
     * Method for unbanning user by specified username.
     *
     * @see AdminService#unBanUserByUsername(String) for additional details.
     */
    @Override
    public void unBanUserByUsername(String username) throws WrongUsernameException {
        UsersEntity userToUnBan = usersService.loadUserByUsername(username);

        /* Admin entered wrong username. */
        if (userToUnBan == null) {
            throw new WrongUsernameException("There is no user, having username: " + username);
        }

        /* User, having specified name, is enabled at the moment. */
        if (userToUnBan.isEnabled()) {
            log.info("IN " + this.getClass()
                            + ", unBanUserByUsername(String username) method"
                            + " - user '{}' wasn't yet banned",
                    username);

            /* Nothing to do then. */
            return;
        }
        /* Now I are sure, that user, having specified username, exists and this user is banned,
         * then I just unban this user. */
        usersService.enableOrDisableUser(true, username);

        log.info("IN " + this.getClass()
                        + ", "
                        + ", unBanUserByUsername(String username) method"
                        + " - user '{}' was unbanned",
                username);
    }
}
