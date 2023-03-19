package niffler.api;

import io.qameta.allure.Step;
import niffler.api.service.RestService;
import niffler.config.NifflerApiProperties;
import niffler.model.User;
import niffler.model.UserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NifflerUserDataClient extends RestService {
    private final Logger LOGGER = LoggerFactory.getLogger(NifflerUserDataClient.class);
    private final NifflerUserDataApi nifflerUserDataApi = retrofit.create(NifflerUserDataApi.class);

    public NifflerUserDataClient() {
        super(NifflerApiProperties.USER_DATA_API);
    }

    @Step
    public UserJson getUserData(User user) {
        LOGGER.info("Get data for user [{}]", user.name());
        UserJson userResponse = null;
        try {
            userResponse = nifflerUserDataApi
                    .getUserData(user.name())
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userResponse;
    }

    @Step
    public UserJson updateUserData(UserJson userJson) {
        LOGGER.info("Set data for user [{}]", userJson.username());
        UserJson userResponse = null;
        try {
            userResponse = nifflerUserDataApi
                    .updateUserData(userJson)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userResponse;
    }
}
