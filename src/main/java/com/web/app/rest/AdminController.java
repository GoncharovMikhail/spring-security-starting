package com.web.app.rest;

import com.web.app.service.AdminService;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.service.exceptions.CantBanAdminException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * A {@link org.springframework.stereotype.Controller} to handle <strong>admin's</strong>
 * requests <strong>only</strong>.
 */
@RestController
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
        String username = json.get("username");
        try {
            adminService.banUserByUsername(username);

            return ResponseEntity.ok("User, having specified username was successfully banned");
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", ban(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. WrongUsernameException occurred");

            return new ResponseEntity<>("There is no user having specified username", HttpStatus.INTERNAL_SERVER_ERROR);
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
        String username = json.get("username");
        try {
            adminService.unBanUserByUsername(username);

            return new ResponseEntity<>("User, having username: '" + username + "' was successfully unbanned",
                    HttpStatus.OK);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", unBan(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. WrongUsernameException occurred");

            return new ResponseEntity<>("There is no user having specified username", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
