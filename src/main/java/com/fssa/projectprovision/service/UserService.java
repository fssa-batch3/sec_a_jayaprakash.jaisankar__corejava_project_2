package com.fssa.projectprovision.service;

import com.fssa.projectprovision.dao.*;
import com.fssa.projectprovision.exception.*;
import com.fssa.projectprovision.model.*;
import com.fssa.projectprovision.validation.*;


import java.util.List;

public class UserService {
    private static final String USER_WITH_EMAIL = "User with email ";

    public String registerUser(User user) throws ServiceException {
        try {
            UserValidator userValidator = new UserValidator(user);
            userValidator.validateAll();

            User existingUser = UserDAO.getUserByEmail(user.getEmail());
            if (existingUser != null && existingUser.isActive()) {
                throw new ServiceException("Email id " + user.getEmail() + " is already registered");
            }
            if (UserDAO.createUser(user)) {
                return "Registration Successful";
            } else {
                return "Registration Failed";
            }
        } catch (ValidationException | DAOException e) {
            throw new ServiceException("Invalid User", e);
        }
    }

    public User loginUser(User user) throws ServiceException {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                throw new ServiceException("Invalid User Credentials");
            }
            if (!UserValidator.validateEmail(user.getEmail())) {
                throw new ValidationException("Invalid Email");
            }
            User fromDb = UserDAO.getUserByEmail(user.getEmail());
            if (fromDb != null && user.getPassword().equals(fromDb.getPassword())) {
                return fromDb;
            } else {
                throw new ServiceException("User Not Found");
            }
        } catch (ValidationException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> getAllUsers() throws ServiceException {
        try {
            return UserDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public User updateUser(User user) throws ServiceException {
        try {
            User existingUser = UserDAO.getUserByEmail(user.getEmail());
            if (existingUser == null) {
                throw new ServiceException(USER_WITH_EMAIL + user.getEmail() + " does not exist.");
            }
            if (!UserDAO.updateUser(user)) {
                throw new ServiceException("User Update Failed");
            }
            return UserDAO.getUserByEmail(user.getEmail());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean deleteUser(String email) throws ServiceException {
        try {
            User existingUser = UserDAO.getUserByEmail(email);
            if (existingUser == null) {
                throw new ServiceException(USER_WITH_EMAIL + email + " does not exist.");
            }
            if (!existingUser.isActive()) {
                throw new ServiceException(USER_WITH_EMAIL + email + " is already inactive.");
            }
            return UserDAO.deleteUserByEmail(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}