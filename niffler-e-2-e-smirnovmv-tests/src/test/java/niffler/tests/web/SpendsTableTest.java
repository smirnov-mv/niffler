package niffler.tests.web;

import niffler.jupiter.Spend;
import niffler.model.CategoryValues;
import niffler.model.CurrencyValues;
import niffler.model.SpendJson;
import org.junit.jupiter.api.*;

public class SpendsTableTest extends BaseTest {
    @Spend(amount = 100.5D, description = "Докторская", category = CategoryValues.PRODUCTS, currency = CurrencyValues.KZT, username = "maks")
    private SpendJson spendJsonFirst;

    @Spend(amount = 25000D, description = "Курсы", username = "maks")
    private SpendJson spendJsonSecond;


    @BeforeEach
    void beforeEach() {
        welcomePage.openPage()
                .shouldBeLoaded()
                .clickLoginButton();

        loginPage.shouldBeLoaded()
                .loginWith("maks", "123");

        mainPage.shouldBeLoaded();
    }

    @Test
    @DisplayName("Check spends with full parameters")
    void checkSpendsWithFullParameters() {
        mainPage.table()
                .checkSpendDataRow(spendJsonFirst);
    }

    @Test
    @DisplayName("Check spends with default parameters")
    void checkSpendsWithDefaultParameters() {
        mainPage.table()
                .checkSpendDataRow(spendJsonSecond);
    }
}