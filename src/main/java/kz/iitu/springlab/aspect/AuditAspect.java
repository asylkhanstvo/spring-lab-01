
package kz.iitu.springlab.aspect;

import kz.iitu.springlab.annotation.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    private static final Logger log =
            LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited)
            throws Throwable {

        long start = System.nanoTime();

        log.info("[AUDIT] START action={}", audited.action());

        if (audited.logArguments()) {
            log.info("[AUDIT] args={}",
                    Arrays.toString(pjp.getArgs()));
        }

        try {
            Object result = pjp.proceed();

            long ms = (System.nanoTime() - start) / 1_000_000;

            log.info("[AUDIT] SUCCESS action={} time={} ms",
                    audited.action(), ms);

            return result;

        } catch (Throwable ex) {

            long ms = (System.nanoTime() - start) / 1_000_000;

            log.error("[AUDIT] FAILED action={} time={} ms error={}",
                    audited.action(), ms, ex.getMessage());

            throw ex;
        }
    }
}
