package niffler.jupiter.spend;

import niffler.api.NifflerSpendClient;
import niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Field;
import java.util.*;

import static niffler.jupiter.spend.DeleteAllSpendsBeforeTestExtension.NAMESPACE_SPENDS_FIELDSLIST;

public class SpendsExtension implements BeforeEachCallback, AfterEachCallback {
    public static final ExtensionContext.Namespace NAMESPACE_SPEND_AND_USER =
            ExtensionContext.Namespace.create(SpendsExtension.class);

    @Override
    public void beforeEach(final ExtensionContext context) {
        Map<String, String> spendAndUser = new HashMap<>();
        var requiredTestInstance = context.getRequiredTestClass();

        List<Field> fieldList = context.getStore(NAMESPACE_SPENDS_FIELDSLIST)
                .get("fieldList", List.class);

        if (fieldList == null) {
            fieldList = Arrays.stream(requiredTestInstance.getDeclaredFields())
                    .filter(f -> f.getType().isAssignableFrom(SpendJson.class))
                    .filter(f -> f.isAnnotationPresent(Spend.class))
                    .toList();
        }

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
            spendAndUser.putIfAbsent(responseSpendJson.id(), responseSpendJson.username());
            field.setAccessible(true);
            try {
                field.set(context.getRequiredTestInstance(), responseSpendJson);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        context.getStore(NAMESPACE_SPEND_AND_USER).put(testUniqueName(context), spendAndUser);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        Map<String, String> spendAndUser = context.getStore(NAMESPACE_SPEND_AND_USER)
                .get(testUniqueName(context), Map.class);
        spendAndUser.forEach((key, value) ->
                HelperSpendApi.deleteSingleSpendForUser(value, key));
    }

    private String testUniqueName(final ExtensionContext context) {
        return context.getRequiredTestClass().getName() + " " + context.getRequiredTestMethod();
    }
}