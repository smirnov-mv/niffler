package niffler.pages.main;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import niffler.allure.HelperAllure;
import niffler.model.SpendJson;
import niffler.utils.DoubleToStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SpendsTableComponent {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final SelenideElement table = $("table.spendings-table");
    private final ElementsCollection spendsRows = table.$$("tbody tr");

    @Step("Find row with spend id [{0}]")
    public SelenideElement findSingleRowBySpendId(String spendId) {
        return rowsBySpendId(spendId)
                .first().scrollTo().ancestor("tr");
    }

    private ElementsCollection rowsBySpendId(String spendId) {
        return $$("input")
                .filter(Condition.value(spendId));
    }

    private SelenideElement rowBySpendId(String spendId) {
        return $$("input")
                .filter(Condition.value(spendId))
                .first().scrollTo().ancestor("tr");
    }

    @Step("Spend with id [{0}] should not exist")
    public void spendIdShouldNotExistInTable(String spendId) {
        rowsBySpendId(spendId).shouldHave(CollectionCondition.size(0));
    }

    @Step("Spend with id [{0}] should exist")
    public void spendIdShouldExistInTable(String spendId) {
        rowsBySpendId(spendId).shouldHave(CollectionCondition.size(1));
    }

    @Step("Check spend data in table row")
    public void checkSpendDataRow(SpendJson spend) {
        var currentRow = rowBySpendId(spend.id());
        HelperAllure.attachElementScreenshot(table, "Table screen");
        assertAll("Check spend data in row",
                () -> checkValue(currentRow, 1, "Date", spend.spendDateAsString()),
                () -> checkValue(currentRow, 2, "Amount", DoubleToStringConverter.convert(spend.amount())),
                () -> checkValue(currentRow, 3, "Currency", spend.currency().name()),
                () -> checkValue(currentRow, 4, "Category", spend.category().getCategoryName()),
                () -> checkValue(currentRow, 5, "Description", spend.description()));
    }


    public void clickSpendCheckboxById(String spendId, Condition condition) {
        var el = $$("input")
                .filter(Condition.value(spendId))
                .first().scrollTo();
        el.click();
        el.should(condition);
    }

    private void checkValue(SelenideElement row, int colIndex, String alias, String expectedValue) {
        step("Check [%s], expected value [%s]".formatted(alias, expectedValue), () ->
                row.$("td", colIndex).as(alias)
                        .should(Condition.exactTextCaseSensitive(expectedValue), Duration.ZERO));
    }

}
