package pro.sky.exever.hogwarts.school;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    public static final Logger logger = Logger.getLogger(TimingExtension.class.getName());
    public static final String START_TIME = "start time";

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Метод [%s] выполнялся %s мс.".formatted(testMethod.getName(), duration));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));

    }
}
