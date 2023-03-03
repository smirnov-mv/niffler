package niffler.utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import niffler.config.NifflerUiProperties;
import niffler.data.User;

import static com.codeborne.selenide.Selenide.open;

public class CookiesSetUp {
    @Step("Set up cookies")
    public static void with(User user) {
        String codeVerifier = SessionCodesGenerator.getCodeVerifier();
        String codeChallenge = SessionCodesGenerator.getCodeChallenge(codeVerifier);
        open(NifflerUiProperties.FAVICON_URL);

        Selenide.sessionStorage().setItem("codeVerifier", codeVerifier);
        Selenide.sessionStorage().setItem("codeChallenge", codeChallenge);
    }
}
