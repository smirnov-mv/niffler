package niffler.tests.web;

import niffler.data.UsersStorage;
import niffler.jupiter.spend.DeleteAllSpendsBeforeTestExtension;
import niffler.jupiter.spend.Spend;
import niffler.model.CategoryValues;
import niffler.model.CurrencyValues;
import niffler.model.SpendJson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DeleteAllSpendsBeforeTestExtension.class)
public class SpendsTableTest extends BaseTest {
    @Spend(amount = 100.5D, description = "Докторская", category = CategoryValues.PRODUCTS, currency = CurrencyValues.KZT, username = "maks")
    private SpendJson spendJsonFirst;

    @Spend(amount = 25000D, description = "Курсы", username = "maks")
    private SpendJson spendJsonSecond;

    @Spend(amount = 50000D, description = "Курсы advanced", username = "maks", category = CategoryValues.STUDY)
    private SpendJson spendJsonThird;


    @BeforeEach
    void beforeEach() {
        welcomePage.openPage()
                .shouldBeLoaded()
                .clickLoginButton();

        loginPage.shouldBeLoaded()
                .loginWith(UsersStorage.MAKS);

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

    @Test
    @DisplayName("Check spends with default parameters")
    void checkSpendsWithSelectedCategory() {
        mainPage.table()
                .checkSpendDataRow(spendJsonThird);
    }
}