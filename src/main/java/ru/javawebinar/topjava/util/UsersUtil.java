package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User(null, "Petr", "petr@mail.ru", "12345", Role.USER, Role.ADMIN),
            new User(null, "Ivan", "ivan@mail.ru", "12345", Role.USER)
    );
}
