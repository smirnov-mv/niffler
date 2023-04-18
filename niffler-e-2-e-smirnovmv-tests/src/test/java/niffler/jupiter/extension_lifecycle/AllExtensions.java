package niffler.jupiter.extension_lifecycle;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.PreconditionViolationException;

public class AllExtensions implements
        BeforeAllCallback,
        BeforeEachCallback,
        BeforeTestExecutionCallback,
        AfterEachCallback,
        AfterTestExecutionCallback,
        AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        methods("beforeAll", context);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        methods("  beforeEach", context);
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        methods("    beforeTestExecution", context);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        methods("    afterTestExecution", context);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        methods("  afterEach", context);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        methods("afterAll", context);
    }

    void methods(String name, ExtensionContext context) {
        try {
            System.out.println(name + " getRequiredTestMethod() = " + context.getRequiredTestMethod());
        } catch (PreconditionViolationException e) {
            System.out.println(name + " getRequiredTestMethod() error with Illegal state");
        }
        try {
            System.out.println(name + " getRequiredTestClass() = " + context.getRequiredTestClass());
        } catch (PreconditionViolationException e) {
            System.out.println(name + " getRequiredTestClass() error with Illegal state");
        }
        try {
            System.out.println(name + " getRequiredTestInstance() = " + context.getRequiredTestInstance());
        } catch (PreconditionViolationException e) {
            System.out.println(name + " getRequiredTestInstance() error with Illegal state");
        }
    }
}