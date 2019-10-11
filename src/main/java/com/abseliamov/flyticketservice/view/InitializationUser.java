package com.abseliamov.flyticketservice.view;

import com.abseliamov.flyticketservice.controller.UserController;
import com.abseliamov.flyticketservice.entity.Role;
import com.abseliamov.flyticketservice.entity.User;
import com.abseliamov.flyticketservice.utils.IOUtil;
import com.abseliamov.flyticketservice.utils.InputData;

public class InitializationUser {
    private UserController userController;

    public InitializationUser(UserController userController) {
        this.userController = userController;
    }

    public void registrationUser() {
        String firstName;
        String lastName;
        String password;
        String confirmPassword;
        boolean createUser = false;

        do {
            firstName = IOUtil.getValidInputData("Enter your first name or '0' to return: ", InputData.STRING);
            if (firstName.isEmpty() || firstName.contains(" ")) {
                System.out.println("\'" + firstName + "\' - it is incorrect value");
                continue;
            } else if (firstName.equals("0")) {
                return;
            }
            lastName = IOUtil.getValidInputData("Enter your last name or '0' to return: ", InputData.STRING);
            if (lastName.isEmpty() || lastName.contains(" ")) {
                System.out.println("\'" + lastName + "\' - it is incorrect value");
                continue;
            } else if (lastName.equals("0")) {
                return;
            }
            password = IOUtil.getValidInputData("Enter your password: ", InputData.STRING);
            if (password.isEmpty() || password.contains(" ")) {
                System.out.println("\'" + password + "\' - it is incorrect value");
                continue;
            } else {
                confirmPassword = IOUtil.getValidInputData("Enter password again or '0' to return: ", InputData.STRING);
                if (!password.equals(confirmPassword)) {
                    System.out.println("\'" + confirmPassword + "\' - it is incorrect value");
                    continue;
                } else if (confirmPassword.equals("0")) {
                    return;
                }
                createUser = true;
            }
            userController.addUser(new User(0, firstName, lastName, password, Role.USER));
        } while (!createUser);
        System.out.println("You have successfully registered.\n\tWelcome to our site!!!\n");
    }

    public void loginUser() {
        String firstName;
        String password;
        boolean loginUser = false;

        do {
            firstName = IOUtil.getValidInputData("Enter your name or '0' to return: ", InputData.STRING);
            if (firstName.isEmpty() || firstName.contains(" ")) {
                System.out.println("\'" + firstName + "\' - it is incorrect value");
                continue;
            } else if (firstName.equals("0")) {
                return;
            }
            password = IOUtil.getValidInputData("Enter your password or '0' to return: ", InputData.STRING);
            if (password.isEmpty() || password.contains(" ")) {
                System.out.println("\'" + password + "\' - it is incorrect value");
                continue;
            } else if (password.equals("0")) {
                return;
            }
            loginUser = userController.loginUser(firstName, password);
            if (!loginUser) {
                System.out.println("The name or password that you entered is not registered." +
                        "\nPlease enter the correct data or register");
            }
        } while (!loginUser);
        System.out.println("*********************************\n" + firstName + " welcome to our site!!!");
    }

    public void logoutUser() {
        userController.logoutUser();
    }
}
