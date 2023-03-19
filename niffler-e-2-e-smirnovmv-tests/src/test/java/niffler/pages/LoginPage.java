package niffler.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import niffler.allure.HelperAllure;
import niffler.config.NifflerUiProperties;
import niffler.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@SuppressWarnings("UnusedReturnValue")
public class LoginPage implements BasePage<LoginPage> {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final SelenideElement pageApp = $("[action='/login']");
    private final SelenideElement usernameInput = $("[name='username']");
    private final SelenideElement passwordInput = $("[name='password']");
    private final SelenideElement signInButton = $("button.form__submit");


    @Step("Open Login Page")
    @Override
    public LoginPage openPage() {
        LOGGER.info("[Step] Open Login Page [{}]", NifflerUiProperties.LOGIN_UI_URL);
        open(NifflerUiProperties.LOGIN_UI_URL);
        return this;
    }

    @Step("Login page should be loaded")
    @Override
    public LoginPage shouldBeLoaded() {
        LOGGER.info("[Check] Login Page should be loaded");
        pageApp.should(Condition.visible);
        return this;
    }

    @Step("Enter username: [{0}]")
    public LoginPage enterUsername(String name) {
        LOGGER.info("[Step] Enter username [{}]", name);
        usernameInput.val(name);
        return this;
    }

    @Step("Enter password: [{0}]")
    public LoginPage enterPassword(String password) {
        LOGGER.info("[Step] Fill Enter [{}]", password);
        passwordInput.val(password);
        return this;
    }

    @Step("Click sign in")
    public LoginPage clickSingIn() {
        LOGGER.info("[Step] Click [Sign in] button");
        signInButton.click();
        return this;
    }

    @Step()
    public void loginWith(User user) {
        HelperAllure.setStepCaseName("Login with user [%s] and password [%s]".formatted(user.name(), user.password()));
        LOGGER.info("[Step] Enter username [{}] and password [{}]", user.name(), user.password());
        enterUsername(user.name());
        enterPassword(user.password());
        clickSingIn();
    }
}
