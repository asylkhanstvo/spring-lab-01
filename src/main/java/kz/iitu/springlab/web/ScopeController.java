package kz.iitu.springlab.webb;

import kz.iitu.springlab.scope.ScopeDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class ScopeController {

    private final ScopeDemoService scopeDemo;

    public ScopeController(ScopeDemoService scopeDemo) {
        this.scopeDemo = scopeDemo;
    }

    @GetMapping("/scopes")
    public Map<String, String> scopes() {
        return Map.of(
                "singleton", scopeDemo.singletonId(),
                "prototypeInjected", scopeDemo.prototypeInjectedId(),
                "prototypeProvider", scopeDemo.prototypeFromProviderId()
        );
    }
}