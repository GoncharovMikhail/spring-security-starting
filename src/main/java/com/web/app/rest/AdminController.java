package com.web.app.rest;

import com.web.app.exceptions.CantBanAdminException;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//todo мне не нравится документация ни к одному из контроллеров. Я нигде не написал про диспатчерсервлет,
// что вообще такое сервлет, что такое контейнер сервлетов(томкат) и как оно работает изнутри.
// но расписывать все это дело слишком долго + с ума сойти можно. Как быть?

/**
 * A {@link RestController} to handle <strong>admin's</strong>
 * requests <strong>only</strong>.
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class AdminController {

    private final AdminService adminService;

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
