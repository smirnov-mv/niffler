package niffler.data;

import niffler.jupiter.user.UserType;
import niffler.model.User;

public class UsersStorage {
    public static User MAKS = new User("maks", "123", UserType.Type.ADMIN);
    public static User USER = new User("user", "123", UserType.Type.USER);
    public static User ADMIN = new User("admin", "123", UserType.Type.ADMIN);
    public static User IVAN = new User("ivan", "123", UserType.Type.USER);
}
