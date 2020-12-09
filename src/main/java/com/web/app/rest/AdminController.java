package com.web.app.rest;

import com.web.app.service.AdminService;
import com.web.app.service.exceptions.WrongUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/management")
    public String management() {
        return "management";
    }

    //todo как это нормально переписать на ResponseEntity<?> ?
    @PostMapping("/ban/{username}")
    public ResponseEntity<?> ban(@PathVariable("username") String username) {
        try {
            adminService.banUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    //todo как это нормально переписать на ResponseEntity<?> ?
    @PostMapping("/unBan")
    public ResponseEntity<?> unBan(@RequestParam String username) {
        try {
            adminService.unBanUserByUsername(username);
        } catch (WrongUsernameException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
