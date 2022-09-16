package com.clearsolutions.registeruser.service;

import com.clearsolutions.registeruser.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateFields(String userId, UserDTO user);

    void deleteUser(String userId);

    List<UserDTO> getUsersByBirthDateRange(LocalDate from, LocalDate to);
}
