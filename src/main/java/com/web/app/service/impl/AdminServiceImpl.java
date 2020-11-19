package com.web.app.service.impl;

import com.web.app.entity.UsersEntity;
import com.web.app.repository.UsersRepository;
import com.web.app.service.AdminService;
import com.web.app.service.exceptions.WrongUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service for admins. All methods in this class can be executed only by admins.
 */
@Service
/* @Slf4j is used for logging */
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UsersRepository usersRepository;

    @Autowired
    public AdminServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Method for banning user by specified username.
     *
     * @see AdminService#ban(String) for additional details.
     */
    @Override
    public void ban(String username) throws WrongUsernameException {
        UsersEntity userToBan = usersRepository.findByUsername(username);

        /* Admin entered wrong username */
        if (userToBan == null) {
            throw new WrongUsernameException("There is no user, having specified name");
        }

        /* User, having specified name, was banned before */
        if (!userToBan.isEnabled()) {
            log.info("IN " + this.getClass()
                            + ", "
                            + this.getClass().getEnclosingMethod().getName()
                            + " - user '{}' was already banned",
                    userToBan.getUsername());
            /* Nothing to do then - just exit */
            return;
        }
        /* Now we are sure, that user, having specified username, exists and he{she} wasn't banned before ->
         * just ban him{her} */
        usersRepository.enableOrDisableUser(false, username);

        log.info("IN " + this.getClass()
                        + ", "
                        + this.getClass().getEnclosingMethod().getName()
                        + " - user '{}' was banned",
                userToBan.getUsername());
    }

    /**
     * Method for unbanning user by specified username.
     *
     * @see AdminService#unBan(String) for additional details.
     */
    @Override
    public void unBan(String username) throws WrongUsernameException {
        UsersEntity userToUnBan = usersRepository.findByUsername(username);

        /* Admin entered wrong username */
        if (userToUnBan == null) {
            throw new WrongUsernameException("There is no user, having specified name");
        }

        /* User, having specified name, is enabled at the moment */
        if (userToUnBan.isEnabled()) {
            log.info("IN " + this.getClass()
                            + ", "
                            + this.getClass().getEnclosingMethod().getName()
                            + " - user '{}' wasn't yet banned",
                    userToUnBan.getUsername());

            /* Nothing to do then */
            return;
        }
        /* Now we are sure, that user, having specified username, exists and this user is banned,
         * then we just unban this user */
        usersRepository.enableOrDisableUser(true, username);

        log.info("IN " + this.getClass()
                        + ", "
                        + this.getClass().getEnclosingMethod().getName()
                        + " - user '{}' was unbanned",
                userToUnBan.getUsername());
    }
}
