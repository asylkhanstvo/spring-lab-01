
package kz.iitu.springlab.aspect;

import kz.iitu.springlab.annotation.Measured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Aspect
@Component
public class MetricsAspect {

    private final Map<String, MethodStats> metrics =
            new ConcurrentHashMap<>();

    @Around("@annotation(measured)")
    public Object measure(
            ProceedingJoinPoint pjp,
            Measured measured
    ) throws Throwable {

        System.out.println(">>> METRICS ASPECT CALLED <<<");

        String method = pjp.getSignature().toShortString();

        long start = System.nanoTime();

        try {
            return pjp.proceed();
        } finally {
            long elapsed = System.nanoTime() - start;

            MethodStats stats = metrics.computeIfAbsent(
                    method, key -> new MethodStats()
            );

            stats.count.increment();
            stats.totalNanos.add(elapsed);

            System.out.println(
                    ">>> METRICS SAVED: " + method + " <<<"
            );
        }
    }

    public Map<String, Map<String, Object>> getMetrics() {

        Map<String, Map<String, Object>> result =
                new TreeMap<>();

        metrics.forEach((method, stats) -> {

            long count = stats.count.sum();
            long totalNanos = stats.totalNanos.sum();

            double totalMs = totalNanos / 1_000_000.0;
            double averageMs = count == 0
                    ? 0
                    : totalMs / count;

            result.put(method, Map.of(
                    "callCount", count,
                    "totalTimeMs", totalMs,
                    "averageTimeMs", averageMs
            ));
        });

        return result;
    }

    private static class MethodStats {
        final LongAdder count = new LongAdder();
        final LongAdder totalNanos = new LongAdder();
    }
}
