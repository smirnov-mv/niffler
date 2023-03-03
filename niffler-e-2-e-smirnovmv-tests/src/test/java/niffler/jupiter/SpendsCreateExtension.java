package niffler.jupiter;

import niffler.api.NifflerSpendClient;
import niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.*;

public class SpendsCreateExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        var requiredTestInstance = context.getRequiredTestInstance();
        List<Field> fieldList = SpendExtensionsHelper.collectTestFields(requiredTestInstance);

        fieldList.forEach(field -> {
            Spend spendFromTest = field.getAnnotation(Spend.class);
            SpendJson spendJson = new SpendJson(
                    null,
                    new Date(),
                    spendFromTest.category(),
                    spendFromTest.currency(),
                    spendFromTest.amount(),
                    spendFromTest.description(),
                    spendFromTest.username());

            SpendJson responseSpendJson = new NifflerSpendClient().createSpend(spendJson);
            field.setAccessible(true);
            try {
                field.set(requiredTestInstance, responseSpendJson);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        });
    }
}