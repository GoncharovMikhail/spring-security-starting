package com.web.app.rest;

import com.web.app.exceptions.CantBanAdminException;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.Map;

/**
 * A {@link RestController} to handle <strong>admin's</strong>
 * requests <strong>only</strong>.
 * <p>
 * A {@code Controller} is a {@code component}, handling user's requests.
 * <p>
 * I learned about {@code Controllers} here:
 * https://www.baeldung.com/spring-controllers
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class AdminController {

    /**
     * A service for executing admin's requests(internally)
     */
    private final AdminService adminService;

    /**
     * Autowire this service.
     * <p>
     * An instance of a class, annotated with
     * <ul>
     *     <li>
     *         {@link Service},
     *     </li>
     *     <li>
     *         {@link org.springframework.stereotype.Repository},
     *     </li>
     * </ul>
     * or any other annotation,
     * which contains a {@link org.springframework.stereotype.Component} inside itself,
     * or even beans from class, annotated with {@link org.springframework.context.annotation.Configuration}
     * will be created and stored in so-called "IOC-container", meaning,
     * {@code Spring} will create an instance of a class,
     * configuring it how by the way (for example, inject another component
     * or inject a field value with the {@link org.springframework.context.annotation.PropertySource} and
     * {@link org.springframework.beans.factory.annotation.Value} annotations) -
     * {@link org.springframework.beans.factory.config.BeanPostProcessor} magic going under the hood
     * (well, not quite magic. If you want to learn more about {@code Spring} internals - better
     * watch some of <i>Eugeniy Borisov's</i> talks on youtube).
     * <strong>only once</strong>,
     * i.e., that instance will be a <strong>singleton</strong> by default,
     * but I can configure this if needed.
     * <p>
     * {@link Autowired} above a method(a constructor as well) means that all method's parameters
     * will be injected from the "IOC-container".
     * <p>
     * <strong>NOTE:</strong> I can also {@code Autowire} components by putting {@link Autowired} above
     * the field I want to {@code Autowire}, but constructor injection is preferred.
     * <p>
     * <strong>NOTE:</strong> prefer inject by interface, not an implementation-class.
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * This method bans a user by specified username.
     *
     * @param json a {@code JSON} object, coming from frontend.
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping("/ban")
    public ResponseEntity<String> ban(@RequestBody Map<String, String> json) {
        final String username = json.get("username");

        try {
            adminService.banUserByUsername(username);

            return ResponseEntity.ok("User, having specified username was successfully banned");
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", ban(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. WrongUsernameException occurred");

            return new ResponseEntity<>("There is no user having specified username",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CantBanAdminException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", ban(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. CantBanAdminException occurred");

            return new ResponseEntity<>("User, having username: '" + username + "' is an admin - can't ban",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method unbans a user by specified username.
     *
     * @param json a {@code JSON} object, coming from frontend.
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping("/unban")
    public ResponseEntity<String> unBan(@RequestBody Map<String, String> json) {
        final String username = json.get("username");

        try {
            adminService.unBanUserByUsername(username);

            return new ResponseEntity<>("User, having username: '" + username + "' was successfully unbanned",
                    HttpStatus.OK);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", unBan(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. WrongUsernameException occurred");

            return new ResponseEntity<>("There is no user having specified username",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
