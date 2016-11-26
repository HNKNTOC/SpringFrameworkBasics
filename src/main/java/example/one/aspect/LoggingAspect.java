package example.one.aspect;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.omg.CORBA.Object;

/**
 * Created by Nikita on 22.11.2016.
 */
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    private void logEventInsideFileLoggers() {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("BEFORE: " +
                joinPoint.getTarget().getClass().getSimpleName() + " " +
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut="allLogEventMethods()",
            returning="retVal")
    public void logAfter(Object retVal) {
        LOGGER.info("AFTER_RET: " + retVal);
    }

    @AfterThrowing(pointcut="allLogEventMethods()",
            throwing="ex")
    public void logAfterThrow(Throwable ex) {
        LOGGER.info("AFTER_THR: " + ex);
    }

}
