package niffler.tests.web;

import com.codeborne.selenide.junit5.TextReportExtension;
import io.qameta.allure.junit5.AllureJunit5;
import niffler.jupiter.AllureSelenideExtension;
import niffler.pages.LoginPage;
import niffler.pages.WelcomePage;
import niffler.pages.main.MainPage;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({TextReportExtension.class,
        AllureJunit5.class,
        AllureSelenideExtension.class})
public class BaseTest {
    final MainPage mainPage = new MainPage();
    final WelcomePage welcomePage = new WelcomePage();
    final LoginPage loginPage = new LoginPage();
}