package com.clearsolutions.registeruser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {

    @NotNull
    private String userId;

    @Email
    @NotNull(message = "Email cannot be missing or empty")
    private String email;

    @NotNull(message = "First name cannot be missing or empty")
    private String firstName;

    @NotNull(message = "Last name cannot be missing or empty")
    private String lastName;

    @Past
    @NotNull(message = "Birth date cannot be missing or empty")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
