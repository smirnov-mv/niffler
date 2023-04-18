package niffler.jupiter.user;

import io.qameta.allure.TmsLink;
import niffler.data.UsersStorage;
import niffler.model.User;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UsersExtension implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        ParameterResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final long TIMEOUT = 30_000;

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(UsersExtension.class);

    private static final Queue<User> USER_ADMIN_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<User> USER_USER_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<User> USER_UNREGISTERED_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        USER_ADMIN_QUEUE.add(UsersStorage.MAKS);
        USER_ADMIN_QUEUE.add(UsersStorage.ADMIN);
        //USER_ADMIN_QUEUE.add(new User("USER_ADMIN", "", UserType.Type.ADMIN));

        USER_USER_QUEUE.add(UsersStorage.USER);
        USER_USER_QUEUE.add(UsersStorage.IVAN);
        //USER_USER_QUEUE.add(new User("USER_USER", "", UserType.Type.USER));

        //USER_UNREGISTERED_QUEUE.add(new User("USER_UNREGISTERED", "", UserType.Type.UNREGISTERED));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Map<String, User> usersForTest = new HashMap<>();

        String id = getTestId(context);

        var parameters = Arrays.stream(context.getRequiredTestMethod()
                        .getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(UserType.class));

        parameters
                .forEach(parameter -> {
                    var currentType = parameter.getAnnotation(UserType.class).type();
                    var currentParameterName = parameter.getName();
                    var currentUser = sortingHat(currentType);
                    usersForTest.putIfAbsent(currentParameterName, currentUser);
                });

        LOGGER.info("Write in namespace [NAMESPACE] users [{}]", usersForTest);
        context.getStore(NAMESPACE).put(id, usersForTest);
    }

    private User sortingHat(UserType.Type type) {
        LOGGER.info("Starting search available user with type [{}]", type);
        LOGGER.info("In queue: [{}] admins, [{}] users, [{}] unregistered",
                USER_ADMIN_QUEUE.size(), USER_USER_QUEUE.size(), USER_UNREGISTERED_QUEUE.size());
        User user = null;
        long endTime = System.currentTimeMillis() + TIMEOUT;
        while (user == null && System.currentTimeMillis() < endTime) {
            user = switch (type) {
                case ADMIN -> USER_ADMIN_QUEUE.poll();
                case USER -> USER_USER_QUEUE.poll();
                case UNREGISTERED -> USER_UNREGISTERED_QUEUE.poll();
            };
            if (user == null) {
                try {
                    LOGGER.info("No user with type [{}] available, sleep 1s. and retry", type);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (user == null) {
            LOGGER.error("No user with type [{}]", type);
            throw new RuntimeException("No user found in queue");
        }
        LOGGER.info("For type [{}] found user [{}]", type, user);
        return user;
    }

    private void deSortingHat(User user) {
        LOGGER.info("Returning user [{}] to the queue", user);
        UserType.Type type = user.type();
        switch (type) {
            case ADMIN -> USER_ADMIN_QUEUE.add(user);
            case USER -> USER_USER_QUEUE.add(user);
            case UNREGISTERED -> USER_UNREGISTERED_QUEUE.add(user);
            default -> throw new RuntimeException("User type [{}] unknown".formatted(type));
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String id = getTestId(context);
        Map<String, User> usersForTest = context.getStore(NAMESPACE).get(id, Map.class);

        usersForTest.forEach((s, user) -> {
            deSortingHat(user);
        });
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(TmsLink.class).value());

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(User.class)
                && parameterContext.getParameter().isAnnotationPresent(UserType.class);
    }

    @Override
    public User resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getTestId(extensionContext);
        return (User) extensionContext.getStore(NAMESPACE).get(id, Map.class)
                .get(parameterContext.getParameter().getName());
    }
}
