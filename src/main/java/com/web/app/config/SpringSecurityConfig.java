package com.web.app.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * A configuration class, storing security configurations.
 * <p>
 *
 * <strong>NOTE:</strong> this class isn't annotated with {@link org.springframework.context.annotation.Configuration}
 * annotation, because {@link EnableWebSecurity} contains it.
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()

                .authorizeRequests()

                /* TODO ПРОВЕРИТЬ ** */
                /* @POST api, available only to admins */
                .antMatchers(
                        "/ban/**",
                        "/unBan/**"
                ).hasRole("ADMIN")

                /* resources */
                .antMatchers(
                        "/styles/**",
                        "/scripts/**"
                ).permitAll()

                /* @GET api */
                .antMatchers(
                        "/welcome",
                        "/signup"
                ).permitAll()

                /* @POST api */
                .antMatchers(
                        "/registration",
                        "/updateAgendaById"
                ).permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/success", true)

                .and()
                .logout().logoutSuccessUrl("/logout");
    }
}
