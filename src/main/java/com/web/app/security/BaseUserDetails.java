package com.web.app.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

/**
 * A simple implementation of {@link UserDetails}, containing only SpringSecurity info.
 * <p>
 * The idea of writing this class was to indicate that in my application:
 * <ul>
 *     <li>
 *         Accounts can't be expired ({@link #isCredentialsNonExpired()}),
 *     </li>
 *     <li>
 *         Accounts can't be locked ({@link #isAccountNonLocked()})
 *     </li>
 *     <li>
 *         credentials can't be expired ({@link #isCredentialsNonExpired()})
 *     </li>
 * </ul>
 */
@Setter
public abstract class BaseUserDetails implements UserDetails {

    @Getter
    private Integer id;

    @Getter
    private Date created;

    @Getter
    private Date updated;

    private boolean enabled;

    @Getter
    private String email;

    private String username;

    private String password;

    private Set<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /* Accounts are never expired */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* Accounts are never locked, but may be disabled */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* Credentials are never expired */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}