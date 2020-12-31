package com.web.app.rest;

import com.web.app.service.AdminService;
import com.web.app.exceptions.WrongUsernameException;
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
     * @return response status, wrapped in {@link ResponseEntity}.
     */
    @PostMapping("/ban")
    public ResponseEntity<?> ban(@RequestBody Map<String, String> json) {
        try {
            String username = json.get("username");
            adminService.banUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", ban(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. An error occurred");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * This method unbans a user by specified username.
     *
     * @param json a {@code JSON} object, coming from frontend.
     * @return response status, wrapped in {@link ResponseEntity}.
     */
    @PostMapping("/unban")
    public ResponseEntity<?> unBan(@RequestBody Map<String, String> json) {
        try {
            String username = json.get("username");
            adminService.unBanUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
            log.info("In " + this.getClass() + ", unBan(@RequestBody Map<String, String> json) method - " +
                    "couldn't ban user. An error occurred");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
