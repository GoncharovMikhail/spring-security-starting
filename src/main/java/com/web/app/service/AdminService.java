package com.web.app.service;

import com.web.app.exceptions.WrongUsernameException;
import com.web.app.service.exceptions.CantBanAdminException;

/**
 * An interface, representing all necessary methods for <strong>admins</strong>
 * (only admins are allowed to execute these methods).
 * <p>
 * <strong>NOTE:</strong> in methods, altering user's data, I pass <strong>username</strong> as a parameter,
 * because on a website, users usually see other's usernames - they, most likely, know nothing about
 * other users ids, emails or any other data.
 */
public interface AdminService {

    /**
     * This method bans user by specified username (is user, having this name wasn't banned before).
     *
     * @param username the specified username.
     * @throws WrongUsernameException if no user with a specified name found.
     */
    void banUserByUsername(String username) throws WrongUsernameException, CantBanAdminException;

    /**
     * This method unbans user by specified username.
     *
     * @param username the specified username.
     * @throws WrongUsernameException if no user with a specified name found.
     */
    void unBanUserByUsername(String username) throws WrongUsernameException;
}
