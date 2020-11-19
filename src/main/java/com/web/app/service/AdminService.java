package com.web.app.service;

import com.web.app.service.exceptions.WrongUsernameException;

/**
 * An interface, representing all necessary methods for admins
 * (only admins are allowed to invoke these methods).
 * <p>
 * <strong>NOTE:</strong> in methods, altering user's data, I pass <i>username</i> as a parameter,
 * because on a website, users usually see other's usernames - they, most likely, know nothing about
 * other users ids emails or any other data.
 */
public interface AdminService {

    /**
     * This method bans user by specified username (is user, having this name wasn't banned before).
     *
     * @param username the specified username.
     * @throws WrongUsernameException if no user with a specified name found.
     */
    void ban(String username) throws WrongUsernameException;

    /**
     * This method unbans user by specified username.
     *
     * @param username the specified username.
     * @throws WrongUsernameException if no user with a specified name found.
     */
    void unBan(String username) throws WrongUsernameException;
}
