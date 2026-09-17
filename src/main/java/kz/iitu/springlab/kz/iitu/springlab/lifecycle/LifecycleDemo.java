package kz.iitu.springlab.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LifecycleDemo {

    private static final Logger log =
            LoggerFactory.getLogger(LifecycleDemo.class);

    private final DateTimeFormatter formatter;

    public LifecycleDemo(DateTimeFormatter formatter) {
        this.formatter = formatter;
        log.info("LifecycleDemo: constructor");
    }

    @PostConstruct
    public void init() {
        log.info("LifecycleDemo: @PostConstruct at {}",
                formatter.format(LocalDateTime.now()));
    }

    @PreDestroy
    public void destroy() {
        log.info("LifecycleDemo: @PreDestroy");
    }
}