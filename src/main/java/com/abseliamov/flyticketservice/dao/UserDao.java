package com.abseliamov.flyticketservice.dao;

import com.abseliamov.flyticketservice.entity.Role;
import com.abseliamov.flyticketservice.entity.User;
import com.abseliamov.flyticketservice.utils.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    private File file = IOUtil.getFile("file.users");
    private final String USERS_FILE_HEADER = "ID, FIRST NAME, LAST NAME, PASSWORD, ROLE";
    private final String COMMA_SEPARATOR = ",";

    @Override
    public void create(User user) {
        boolean userExist = false;
        long userId = 1;
        List<User> users = getAll();
        if (users.size() == 0) {
            users.add(new User(userId, user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole()));
            writeToFile(users);
            userExist = true;
        } else {
            for (User userItem : users) {
                if (userItem.equals(user)) {
                    System.out.println("Such user already exists.");
                    userExist = true;
                    break;
                }
            }
        }
        if (!userExist) {
            long newId = getId(users);
            users.add(new User(newId, user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole()));
            writeToFile(users);
        }
    }

    @Override
    public User getById(long id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(User user) {
        List<User> users = getAll();
        List<User> updateList = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User userItem : users) {
                if (userItem.getId() == user.getId()) {
                    updateList.add(user);
                    continue;
                }
                updateList.add(userItem);
            }
            writeToFile(updateList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public void delete(User user) {
        List<User> users = getAll();
        List<User> deleteList = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User userItem : users) {
                if (userItem.getId() == user.getId()) {
                    continue;
                }
                deleteList.add(userItem);
            }
            writeToFile(deleteList);
        } else {
            System.out.println("Error " + file + " is empty.");
        }
    }

    @Override
    public User parseDataFromFile(String[] userData) {
        User user = new User(
                Long.parseLong(userData[0]),
                userData[1],
                userData[2],
                userData[3],
                Role.valueOf(userData[4]));
        return user;
    }

    @Override
    public StringBuilder buildDataToFile(List<User> users) {
        StringBuilder builder = new StringBuilder();
        builder.append(USERS_FILE_HEADER);
        for (User userItem : users) {
            builder.append("\n");
            builder.append(userItem.getId());
            builder.append(COMMA_SEPARATOR);
            builder.append(userItem.getFirstName());
            builder.append(COMMA_SEPARATOR);
            builder.append(userItem.getLastName());
            builder.append(COMMA_SEPARATOR);
            builder.append(userItem.getPassword());
            builder.append(COMMA_SEPARATOR);
            builder.append(userItem.getRole().toString());
        }
        return builder;
    }

    public List<User> getAll() {
        return getAll(file, USERS_FILE_HEADER);
    }

    public User getUserByItem(String user){
        String[] userId = user.split(",");
        return getById(Long.parseLong(userId[0].trim()));
    }

    private void writeToFile(List<User> users) {
        writeToFile(file, users);
    }

    long getId(List<User> users){
        final long[] id = {1};
        users.forEach(user -> {
            if (user.getId() >= id[0]){
                id[0] = user.getId() + 1;
            }
        });
        return id[0];
    }
}
