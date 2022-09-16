package com.clearsolutions.registeruser.controller;

import com.clearsolutions.registeruser.dto.UserDTO;
import com.clearsolutions.registeruser.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateFields(@PathVariable(value = "userId") String userId,
                                                @RequestBody UserDTO user) {

        return ResponseEntity.ok(userService.updateFields(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable(name = "userId") String userId) {

        userService.deleteUser(userId);
    }

    @GetMapping("/get/{from}/{to}")
    public ResponseEntity<List<UserDTO>> getUsersByBirthDateRange(@PathVariable(name = "from") String from,
                                                                  @PathVariable(name = "to") String to) {

        LocalDate dateFrom = LocalDate.parse(from);
        LocalDate dateTo = LocalDate.parse(to);

        return ResponseEntity.ok(userService.getUsersByBirthDateRange(dateFrom, dateTo));
    }
}
