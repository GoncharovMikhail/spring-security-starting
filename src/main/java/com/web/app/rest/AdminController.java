package com.web.app.rest;

import com.web.app.service.AdminService;
import com.web.app.service.exceptions.WrongUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/ban")
    public ResponseEntity<?> ban(@RequestBody Map<String, String> json) {
        try {
            final String username = json.get("username");
            adminService.banUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/unban")
    public ResponseEntity<?> unBan(@RequestBody Map<String, String> json) {
        try {
            final String username = json.get("username");
            adminService.unBanUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
