package com.web.app.config;

import com.web.app.model.SignUpRequestDTO;
import com.web.app.security.UsersDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A {@link org.springframework.context.annotation.Configuration} class, storing security configurations.
 * <p>
 *
 * <strong>NOTE:</strong> this class isn't annotated with {@link org.springframework.context.annotation.Configuration}
 * annotation, because {@link EnableWebSecurity} contains it. Better read documentation of this annotation here:
 * https://docs.spring.io/spring-security/site/docs/4.2.x/apidocs/org/springframework/security/config/annotation/web/configuration/EnableWebSecurity.html
 */
@EnableWebSecurity
/* The <pre> @PropertySource("...") </pre> gets a relative path (to the <pre> "resources" </pre> folder)
 * to a property-file to read properties from it.
 *
 * In combination with <pre> @Value("${...}") </pre>, declared above a field,
 * we can inject a value into the annotated field, which was read
 * from the specified property-file. */
@PropertySource("properties/security/passwordencoder.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /* <pre> Value("${...}") </pre> gets a property's name as a parameter, then finds it in the property-file
     * (specified in <pre> @PropertySource("...") </pre> annotation), and sets it to the annotated field.
     * If there is no property, having specified name, then nothing will be injected in the field.
     * In this case, we inject number of rounds of encoding(the log rounds to use, between 4 and 31).
     *
     * <strong>Note:</strong>: write name of a property in <pre> ${...} </pre> - otherwise,
     * property won't be injected to the annotated field. */
    @Value("${password.encoder.strength}")
    private Integer passwordEncoderStrength;

    /**
     * A {@link PasswordEncoder} bean to use.
     * <p>
     * {@code PasswordEncoder} is used to encode user's password before saving it in the database
     * ({@link com.web.app.service.impl.UsersServiceImpl#saveUserInDatabase(SignUpRequestDTO)}) or
     * in-memory saving or any other type of persisting user's password.
     * <p>
     * Also, it is used internally by Spring security:
     * whenever user tries to log in, he/she specifies username and password.
     * Then, a <pre> POST </pre> request, containing specified username and password is sent to the server.
     * {@code Spring Security} loads user's security data
     * (represented as {@link org.springframework.security.core.userdetails.UserDetails})
     * by specified username.
     * <p>
     * Its necessary to implement the {@link org.springframework.security.core.userdetails.UserDetailsService}
     * interface, particularly, the
     * {@link org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(String)}
     * method. As I mentioned before, user's data may be stored in a several ways(in the database,
     * in-memory, e.t.c), so loading a user by username can be proceeded in a several different ways
     * as well.
     * <p>
     * If there is no user with the specified username,
     * the {@link org.springframework.security.core.userdetails.UsernameNotFoundException} is thrown.
     * Otherwise, Spring security loads {@code UserDetails}, which is <strong>never null</strong>.
     * After that, the password from the <pre> POST </pre> request get encoded, and then,
     * {@code Spring Security} checks, if passwords, stored in loaded {@code UserDetails} and the one from the
     * <pre> POST </pre> request encoded as well, matches.
     * <p>
     * The idea of using {@code PasswordEncoder} makes sense:
     * if the attacker hacked our database and tries to login -
     * he inputs someone's username and <strong>encoded</strong> password.
     * Then, these username and password goes to server, inputted
     * (already encoded password, hence attacker took it from my database), gets encoded one more time
     * and it is almost impossible that the real password and the one, entered by attacker, will
     * coincide.
     * <p>
     *
     * <strong>NOTE:</strong> any encoded password <strong>never</strong> gets decoded.
     * <p>
     *
     * <strong>NOTE:</strong> if two passwords matches, this <strong>doesn't</strong> mean they are equal.
     * Encoding and matching algorithms are complicated.
     * For example, if you encode a string for a few times, you may get different hashed ones.
     * You may try this out here:
     * https://bcrypt-generator.com/
     * Try to encode something for a few times!
     * See documentation for {@link BCryptPasswordEncoder} here:
     * http://docs.spring.io/spring-security/site/docs/3.2.5.RELEASE/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
     * <p>
     *
     * @see com.web.app.security.UsersDetailsService - my implementation of the {@code UserDetailsService} interface.
     * @see UsersDetails - my implementation of the {@code UserDetails} interface.
     * @see BCryptPasswordEncoder#BCryptPasswordEncoder(int) for more info about the "strength" parameter.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordEncoderStrength);
    }

    /**
     * This method does lots of security stuff. Better explain step-by-step in the code.
     *
     * @param httpSecurity http to configure.
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                /* We can configure 3 types of user's <pre> authentication </pre>:
                 * <pre>
                 *  <ul>
                 *      <li>
                 *          <pre> Basic authentication </pre>
                 *      </li>
                 *      <li>
                 *          <pre> Form-based authentication </pre>
                 *      </li>
                 *      <li>
                 *          <pre> Token authentication(for example, JWT) </pre>
                 *      </li>
                 *  </ul>
                 * </pre>
                 * We'll use <pre> Form-based authentication </pre> because it's easy enough and most-commonly used. */
                .httpBasic().disable()

                /* Disable <pre> csrf </pre>:
                 * https://docs.spring.io/spring-security/site/docs/4.1.x/reference/html/csrf.html */
                .csrf().disable()

                /* Allows restricting access based upon the requests via <pre> URL </pre> patterns */
                .authorizeRequests()

                /* Allow resources (<pre> .js </pre>, and <pre> .css </pre> files) for everyone */
                .antMatchers(
                        "/styles/**",
                        "/scripts/**"
                ).permitAll()

                /* Define <pre> POST </pre> api, available <strong>only</strong> to admins. */
                .antMatchers(
                        "/ban",
                        "/unban"
                ).hasRole("ADMIN")

                /* Define <pre> GET </pre> api, allowed to <strong>all</strong> users.
                 * https://docs.spring.io/spring-security/site/docs/3.0.x/reference/anonymous.html */
                .antMatchers(
                        "/welcome",
                        "/signup",
                        "/wrongusername"
                ).permitAll()

                /* Define <pre> GET </pre> api, available to <strong>all</strong> users. */
                .antMatchers(
                        "/search"
                ).permitAll()

                /* Define <pre> POST </pre> api, available to <strong>all</strong> users. */
                .antMatchers(
                        "/registration"
                ).permitAll()

                /* Any other api is available only to <strong>authenticated</pre> users. */
                .anyRequest().authenticated()

                .and()

                /* Define the <pre> login </pre> form url, and if user successfully logs in,
                 * redirect him{her} to <pre> success </pre> page. */
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/success", true)

                .and()
                /* Define the <pre> logout </pre> url, permit it to <strong>all</strong> users. */
                .logout().logoutSuccessUrl("/logout").permitAll()

                /* Invalidate current <pre> http session </pre>. */
                .invalidateHttpSession(true)

                /* Invalidate all <pre> cookies </pre>, storing user's data. */
                .deleteCookies("JSESSIONID")

                /* Define the url right after logging out. */
                .logoutSuccessUrl("/welcome");
    }
}
