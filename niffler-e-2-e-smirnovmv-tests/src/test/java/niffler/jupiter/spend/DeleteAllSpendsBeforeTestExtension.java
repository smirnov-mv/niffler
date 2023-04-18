package niffler.jupiter.spend;

import niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeleteAllSpendsBeforeTestExtension implements BeforeAllCallback {
    public static final ExtensionContext.Namespace NAMESPACE_SPENDS_FIELDSLIST =
            ExtensionContext.Namespace.create(DeleteAllSpendsBeforeTestExtension.class);
    @Override
    public void beforeAll(final ExtensionContext context) {
        Set<String> userNames = new HashSet<>();
        var requiredTestInstance = context.getRequiredTestClass();

        List<Field> fieldList = Arrays.stream(requiredTestInstance.getDeclaredFields())
                .filter(f -> f.getType().isAssignableFrom(SpendJson.class))
                .filter(f -> f.isAnnotationPresent(Spend.class))
                .toList();

        context.getStore(NAMESPACE_SPENDS_FIELDSLIST).put("fieldList", fieldList);

        fieldList.forEach(field -> {
            userNames.add(field.getAnnotation(Spend.class).username());
        });
        HelperSpendApi.deleteSpends(userNames);
    }
}
