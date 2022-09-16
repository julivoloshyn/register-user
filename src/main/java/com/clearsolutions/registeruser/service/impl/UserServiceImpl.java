package com.clearsolutions.registeruser.service.impl;

import com.clearsolutions.registeruser.dto.UserDTO;
import com.clearsolutions.registeruser.errorhandling.exeptions.NotNullFieldsException;
import com.clearsolutions.registeruser.errorhandling.exeptions.NotValidDateArguments;
import com.clearsolutions.registeruser.errorhandling.exeptions.UserNotFoundException;
import com.clearsolutions.registeruser.errorhandling.exeptions.UserNotValidAge;
import com.clearsolutions.registeruser.model.UserModel;
import com.clearsolutions.registeruser.repository.UserRepository;
import com.clearsolutions.registeruser.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Environment env;

    @Autowired
    UserRepository userRepository;

    ModelMapper mapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO user) {

        if (user.getEmail() == null || user.getFirstName() == null
                || user.getLastName() == null || user.getBirthDate() == null) {

            throw new NotNullFieldsException();
        }
        Period period = Period.between(user.getBirthDate(), LocalDate.now());

        if (period.getYears() < Integer.parseInt(Objects.requireNonNull(env.getProperty("age")))) {
            throw new UserNotValidAge();
        }

        UserModel userModel = mapper.map(user, UserModel.class);
        return mapper.map(
                userRepository.save(userModel), UserDTO.class
        );
    }

    @Override
    public UserDTO updateFields(String userId, UserDTO user) {

        UserDTO updatedUser = mapper.map(userRepository.findById(userId), UserDTO.class);

        if (updatedUser == null) {
            throw new UserNotFoundException();
        }

        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        if (user.getFirstName() != null) {
            updatedUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            updatedUser.setLastName(user.getLastName());
        }
        if (user.getBirthDate() != null) {
            updatedUser.setBirthDate(user.getBirthDate());
        }
        if (user.getAddress() != null) {
            updatedUser.setAddress(user.getAddress());
        }
        if (user.getPhoneNumber() != null) {
            updatedUser.setPhoneNumber(user.getPhoneNumber());
        }

        UserModel userModel = mapper.map(updatedUser, UserModel.class);

        return mapper.map(
                userRepository.save(userModel), UserDTO.class
        );
    }

    @Override
    public void deleteUser(String userId) {
        UserModel userModel = mapper.map(userRepository.findById(userId), UserModel.class);

        if (userModel == null) {
            throw new UserNotFoundException();
        }

        userRepository.delete(userModel);
    }

    @Override
    public List<UserDTO> getUsersByBirthDateRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to)) {
            throw new NotValidDateArguments();
        }

        List<UserModel> allUsers = userRepository.findAll();
        List<UserDTO> requiredUsers = new ArrayList<>();

        for (UserModel user : allUsers) {
            if (user.getBirthDate().isAfter(from)
                    && user.getBirthDate().isBefore(to)) {

                UserDTO userDTO = mapper.map(user, UserDTO.class);
                requiredUsers.add(userDTO);
            }
        }

        return requiredUsers;
    }
}
