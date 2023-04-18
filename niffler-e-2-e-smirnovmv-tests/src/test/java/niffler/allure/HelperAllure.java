package niffler.allure;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Parameter;
import org.openqa.selenium.OutputType;

import java.util.List;

import static io.qameta.allure.Allure.getLifecycle;

public class HelperAllure {

    public static void attachScreenshotFullPage(String screenName) {
        addScreenshot(screenName);
    }

    public static void attachElementScreenshot(SelenideElement element, String attachName) {
        addElementScreenshot(element, attachName);
    }

    public static void setStepCaseName(String name) {
        getLifecycle().updateStep(stepResult ->
                stepResult.setName(name)
        );
    }

    public static void addStepCustomParameters(List<Parameter> parameters) {
        getLifecycle().updateStep(stepResult ->
                stepResult.setParameters(parameters)
        );
    }

    @Attachment(value = "{attachName}", type = "image/png")
    private static byte[] addScreenshot(String attachName) {
        return (Selenide.screenshot(OutputType.BYTES));
    }

    @Attachment(value = "{attachName}", type = "image/png")
    private static byte[] addElementScreenshot(SelenideElement element, String attachName) {
        return element.getScreenshotAs(OutputType.BYTES);
    }
}
