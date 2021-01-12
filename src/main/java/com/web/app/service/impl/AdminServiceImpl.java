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

    private final UsersService usersService;

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
        /* Now we are sure, that user, having specified username, exists and he{she} wasn't banned before,
         * then, we just ban this user. */
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
        /* Now we are sure, that user, having specified username, exists and this user is banned,
         * then we just unban this user. */
        usersService.enableOrDisableUser(true, username);

        log.info("IN " + this.getClass()
                        + ", "
                        + ", unBanUserByUsername(String username) method"
                        + " - user '{}' was unbanned",
                username);
    }
}
