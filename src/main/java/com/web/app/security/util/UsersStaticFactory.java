package com.web.app.security.util;

import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.security.UsersDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * An util class, used to convert(map) {@link UsersEntity} to {@link UsersDetails}
 * with the {@link #mapEntityToUsersDetails(UsersEntity)}
 */
public final class UsersStaticFactory {

    private UsersStaticFactory() {
        throw new AssertionError(this.getClass() + "can't be instantiated");
    }

    public static UsersDetails mapEntityToUsersDetails(UsersEntity usersEntity) {
        return new UsersDetails(
                usersEntity.isEnabled(),
                usersEntity.getUsername(),
                usersEntity.getPassword(),
                mapRolesToGrantedAuthorities(usersEntity.getRoles())
        );
    }

    private static Set<GrantedAuthority> mapRolesToGrantedAuthorities(Set<RolesEntity> rolesEntities) {
        return rolesEntities.stream()
                .map(rolesEntity -> new SimpleGrantedAuthority(rolesEntity.getRole()))
                .collect(Collectors.toSet());
    }
}