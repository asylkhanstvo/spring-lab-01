package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("titlecase")
@Order(3)
public class TitleCaseNotifier implements Notifier {

    @PostConstruct
    public void init() {
        System.out.println("TitleCaseNotifier initialized");
    }

    @Override
    public String send(String message) {
        if (message == null || message.isBlank()) {
            return "";
        }

        return java.util.Arrays.stream(message.trim().split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0))
                        + word.substring(1).toLowerCase())
                .collect(java.util.stream.Collectors.joining(" "));
    }

    @Override
    public String channel() {
        return "titlecase";
    }
}