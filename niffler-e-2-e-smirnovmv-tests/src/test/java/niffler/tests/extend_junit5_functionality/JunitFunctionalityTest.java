package niffler.tests.extend_junit5_functionality;


import niffler.jupiter.extension_lifecycle.AllExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({
        AllExtensions.class
})
public class JunitFunctionalityTest {

    //1) Посмотреть эксперементально – написав код, – на каком этапе жизненного цикла Extension работают методы
    // ExtensionContext.getRequiredTestMethod(),
    // ExtensionContext.getRequiredTestClass(),
    // ExtensionContext.getRequiredTestInstance()

    @Test
    void simpleName() {
        System.out.println("I'm a simple test");
    }

    //getRequiredTestMethod() и getRequiredTestInstance() выбрасывают
    //Illegal state для beforeAll и afterAll.
    //Во всех остальных случаях все три метода возвращают данные
}
