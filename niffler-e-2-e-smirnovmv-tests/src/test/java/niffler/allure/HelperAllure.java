package niffler.allure;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

public class HelperAllure {

    public static void attachScreenshotFullPage(String screenName) {
        addScreenshot(screenName);
    }

    @Attachment(value = "{attachName}", type = "image/png")
    private static byte[] addScreenshot(String attachName) {
        return (Selenide.screenshot(OutputType.BYTES));
    }
}
