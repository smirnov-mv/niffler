package niffler.model;

import niffler.jupiter.user.UserType;

public record User(String name, String password, UserType.Type type) {
}