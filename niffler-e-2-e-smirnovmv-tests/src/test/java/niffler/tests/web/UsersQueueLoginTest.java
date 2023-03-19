package niffler.tests.web;

import io.qameta.allure.TmsLink;
import niffler.jupiter.user.UserType;
import niffler.jupiter.user.UsersExtension;
import niffler.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UsersExtension.class)
public class UsersQueueLoginTest extends BaseTest {
    private void loginWithUser(User user) {
        welcomePage.openPage()
                .shouldBeLoaded()
                .clickLoginButton();

        loginPage.shouldBeLoaded()
                .loginWith(user);

        mainPage.shouldBeLoaded();
    }

    @Test
    @TmsLink("TC-01")
    @DisplayName("Should be successful login TC-01")
    void shouldBeSuccessfulLogin1(@UserType(type = UserType.Type.ADMIN) User user) {
        System.out.println("shouldBeSuccessfulLogin1\n" +
                "user [%s]".formatted(user));
        loginWithUser(user);
    }

    @Test
    @TmsLink("TC-02")
    @DisplayName("Should be successful login TC-02")
    void shouldBeSuccessfulLogin2(@UserType(type = UserType.Type.ADMIN) User user) {
        System.out.println("shouldBeSuccessfulLogin2\n" +
                "user [%s]".formatted(user));
        loginWithUser(user);
    }

    @Test
    @TmsLink("TC-03")
    @DisplayName("Should be successful login TC-03")
    void shouldBeSuccessfulLogin3(@UserType(type = UserType.Type.ADMIN) User user) {
        System.out.println("shouldBeSuccessfulLogin3\n" +
                "user [%s]".formatted(user));
        loginWithUser(user);
    }

    @Test
    @TmsLink("TC-04")
    @DisplayName("Should be successful login TC-04")
    void shouldBeSuccessfulLogin4(@UserType User user) {
        System.out.println("shouldBeSuccessfulLogin4\n" +
                "user [%s]".formatted(user));
        loginWithUser(user);
    }

    @Test
    @TmsLink("TC-05")
    @DisplayName("Should be successful login TC-05")
    void shouldBeSuccessfulLogin5(@UserType User user) {
        System.out.println("shouldBeSuccessfulLogin5\n" +
                "user [%s]".formatted(user));
        loginWithUser(user);
    }

    @Test
    @TmsLink("TC-06")
    @DisplayName("Should be successful login TC-06")
    void shouldBeSuccessfulLogin6(@UserType(type = UserType.Type.ADMIN) User admin,
                                  @UserType(type = UserType.Type.ADMIN) User admin2,
                                  @UserType(type = UserType.Type.USER) User user,
                                  @UserType(type = UserType.Type.USER) User user2) {
        System.out.println(("shouldBeSuccessfulLogin6\n" +
                "admin [%s]\n" +
                "admin2 [%s]\n" +
                "user [%s]\n" +
                "user2 [%s]").formatted(admin, admin2, user, user2));
        loginWithUser(user);
    }
}
