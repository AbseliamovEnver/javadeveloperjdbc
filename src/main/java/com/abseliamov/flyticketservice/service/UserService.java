package com.abseliamov.flyticketservice.service;

import com.abseliamov.flyticketservice.dao.UserDao;
import com.abseliamov.flyticketservice.entity.Role;
import com.abseliamov.flyticketservice.entity.User;
import com.abseliamov.flyticketservice.utils.CurrentUser;

import java.util.List;

public class UserService implements ServiceInterface<User> {
    private UserDao userDAO;
    private CurrentUser currentUser;

    public UserService(UserDao userDAO, CurrentUser currentUser) {
        this.userDAO = userDAO;
        this.currentUser = currentUser;
    }

    @Override
    public void add(User user) {
        userDAO.create(user);
    }

    @Override
    public User getById(long id) {
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    public boolean loginUser(String firstName, String password) {
        User foundUser = userDAO.getAll().stream()
                .filter(user -> user.getFirstName().equals(firstName))
                .filter(user -> user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (foundUser != null) {
            currentUser.setUser(foundUser);
            return true;
        }
        return false;
    }

    public void logoutUser() {
        if (currentUser != null) {
            currentUser.setUser(null);
        }
    }

    public void setUserRole(User updateUser, Role role) {
        if (CurrentUser.getInstance().getUser().getRole() == Role.ADMIN) {
            switch (role) {
                case ADMIN:
                    updateUser.setRole(Role.ADMIN);
                    break;
                case USER:
                    updateUser.setRole(Role.USER);
                    break;
                default:
                    updateUser.setRole(Role.GUEST);
                    break;
            }
        } else {
            System.out.println("You do not have administrative permissions.");
        }
    }
}
