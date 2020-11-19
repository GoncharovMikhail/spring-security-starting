package com.web.app.security;

import com.web.app.entity.AgendaEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.Set;

/**
 * A custom implementation of {@link org.springframework.security.core.userdetails.UserDetails}, storing all necessary
 * information for Spring security <strong>and</strong> user's agenda.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails extends BaseUserDetails {

    private Set<AgendaEntity> agendas;

    public CustomUserDetails(Integer id,
                             Date created,
                             Date updated,
                             boolean enabled,
                             String email,
                             String username,
                             String password,
                             Set<? extends GrantedAuthority> authorities,
                             Set<AgendaEntity> agendas) {
        this.setId(id);
        this.setCreated(created);
        this.setUpdated(updated);
        this.setEnabled(enabled);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
        this.setAuthorities(authorities);
        this.agendas = agendas;
    }
}
