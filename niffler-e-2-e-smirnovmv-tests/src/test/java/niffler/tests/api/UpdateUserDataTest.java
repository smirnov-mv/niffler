package niffler.tests.api;

import io.qameta.allure.Step;
import niffler.api.NifflerUserDataClient;
import niffler.jupiter.user.UserData;
import niffler.model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserDataTest {
    private final NifflerUserDataClient client = new NifflerUserDataClient();


    @ValueSource(strings = {
            "data/user1.json",
            "data/user2.json"
    })

    @ParameterizedTest
    @DisplayName("Update user data")
    void updateUserData(@UserData UserJson userJson) {
        UserJson updatedUserJson = client.updateUserData(userJson);
        assertAll(
                () -> checkUserField("username", userJson.username(), updatedUserJson.username()),
                () -> checkUserField("firstname", userJson.firstname(), updatedUserJson.firstname()),
                () -> checkUserField("surname", userJson.surname(), updatedUserJson.surname()),
                () -> checkUserField("currency", userJson.currency(), updatedUserJson.currency()),
                () -> checkUserField("photo", userJson.photo(), updatedUserJson.photo())
        );

    }

    @Step("[{0}], expected [{1}], actual [{2}]")
    private void checkUserField(String field, Object expected, Object actual) {
        assertEquals(expected, actual);
    }
}
