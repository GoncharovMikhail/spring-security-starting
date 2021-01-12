package com.web.app.security.util;

import com.web.app.entity.RolesEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.security.UsersDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * An util class, used to convert(in other words, map) {@link UsersEntity} to {@link UsersDetails}
 * with the {@link #mapEntityToUsersDetails(UsersEntity)}
 */
public final class UsersDetailsUtils {

    private UsersDetailsUtils() {
        throw new AssertionError(this.getClass() + "can't be instantiated");
    }

    /**
     * This method converts(maps) an {@code Entity} to {@link UsersDetails}.
     *
     * @param usersEntity entity to map.
     * @return {@code UsersDetails} instance
     */
    public static UsersDetails mapEntityToUsersDetails(UsersEntity usersEntity) {
        return new UsersDetails(
                usersEntity.isEnabled(),
                usersEntity.getUsername(),
                usersEntity.getPassword(),
                mapRolesToGrantedAuthorities(usersEntity.getRoles())
        );
    }

    /**
     * This method converts(maps) {@link Set<RolesEntity>} to {@link Set<GrantedAuthority>}.
     *
     * @param rolesEntities set to map.
     * @return {@link Set<GrantedAuthority>}.
     */
    private static Set<GrantedAuthority> mapRolesToGrantedAuthorities(Set<RolesEntity> rolesEntities) {
        return rolesEntities.stream()
                .map(rolesEntity -> new SimpleGrantedAuthority(rolesEntity.getRole()))
                .collect(Collectors.toSet());
    }
}