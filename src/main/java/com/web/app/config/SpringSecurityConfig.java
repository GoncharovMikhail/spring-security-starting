package com.web.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * A configuration class, storing security configurations.
 */
@EnableWebSecurity
@Slf4j
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
