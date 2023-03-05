package niffler.jupiter;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.List;

public class SpendDeleteAfterEachExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) {
        var requiredTestInstance = context.getRequiredTestInstance();
        List<Field> fieldList = SpendExtensionsHelper.collectTestFields(requiredTestInstance);
        SpendExtensionsHelper.deleteSpends(fieldList);
    }
}
