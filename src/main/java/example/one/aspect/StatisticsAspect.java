package example.one.aspect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Nikita on 26.11.2016.
 */
public class StatisticsAspect {
    private Map<Class<?>, Integer> counter = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(StatisticsAspect.class);

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @AfterReturning("allLogEventMethods()")
    public void count(JoinPoint jp) {
        Class<?> clazz = jp.getTarget().getClass();
        if (!counter.containsKey(clazz)) {
            counter.put(clazz, 0);
        }
        counter.put(clazz, counter.get(clazz) + 1);
    }

    public Map<Class<?>, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    public void showStatisticsInLog(){
        LOGGER.debug("destroy");
        LOGGER.info("Statistics = "+counter);
    }

    @AfterReturning("execution(* com.yet.spring.core.App.logEvents(..))")
    public void outputLoggingCounter() {
        LOGGER.info("Loggers statistics. Number of calls: ");
        for (Entry<Class<?>, Integer> entry : counter.entrySet()) {
            LOGGER.info("    " + entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }
}
