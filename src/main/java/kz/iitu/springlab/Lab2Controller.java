package kz.iitu.springlab.webb;

import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.service.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final Notifier titleCaseNotifier;

    public Lab2Controller(
            NotificationService notifications,
            @Qualifier("titlecase") Notifier titleCaseNotifier) {

        this.notifications = notifications;
        this.titleCaseNotifier = titleCaseNotifier;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(
            @RequestParam(defaultValue = "Hello") String text) {

        return Map.of(
                "primary", notifications.viaPrimary(text),
                "console", notifications.viaConsole(text),
                "all", notifications.viaAll(text),
                "beanNames", notifications.names()
        );
    }

    @GetMapping("/custom")
    public String custom(
            @RequestParam(defaultValue = "Hello Spring Lab") String text) {

        return titleCaseNotifier.send(text);
    }
}