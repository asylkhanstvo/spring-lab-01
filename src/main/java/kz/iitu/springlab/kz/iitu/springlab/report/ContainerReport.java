package kz.iitu.springlab.report;

import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ContainerReport implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(ContainerReport.class);

    private final ApplicationContext context;
    private final NotificationService notificationService;

    public ContainerReport(
            ApplicationContext context,
            NotificationService notificationService) {

        this.context = context;
        this.notificationService = notificationService;
    }

    @Override
    public void run(String... args) {

        String[] beanNames = context.getBeanDefinitionNames();

        long notifierCount = Arrays.stream(beanNames)
                .map(name -> context.getBean(name))
                .filter(bean -> bean instanceof Notifier)
                .count();

        log.info("=== Container Report ===");
        log.info("Bean definition count: {}", beanNames.length);
        log.info("Notifier implementations: {}", notifierCount);
        log.info("NotificationService type: {}",
                notificationService.getClass().getName());

        Arrays.stream(beanNames)
                .filter(name -> name.equals("console")
                        || name.equals("email")
                        || name.equals("noop"))
                .forEach(name -> log.info("Notifier bean: {}", name));
    }
}