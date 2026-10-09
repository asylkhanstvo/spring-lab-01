
package kz.iitu.springlab.web;

import kz.iitu.springlab.service.CatalogService;
import kz.iitu.springlab.aspect.MetricsAspect;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;
    private final MetricsAspect metricsAspect;

    public CatalogController(
            CatalogService catalogService,
            MetricsAspect metricsAspect) {
        this.catalogService = catalogService;
        this.metricsAspect = metricsAspect;
    }

    @GetMapping("/item/{id}")
    public String findById(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/items")
    public List<String> findAll(
            @RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    @DeleteMapping("/item/{id}")
    public String remove(@PathVariable long id) {
        return catalogService.remove(id);
    }

    @GetMapping("/proxy")
    public String checkProxy() {
        boolean isProxy = AopUtils.isAopProxy(catalogService);
        boolean isCglib = AopUtils.isCglibProxy(catalogService);

        return "Is AOP Proxy: " + isProxy
                + ", Is CGLIB Proxy: " + isCglib;
    }

    @GetMapping("/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }

    @GetMapping("/metrics")
    public Map<String, Map<String, Object>> getMetrics() {
        return metricsAspect.getMetrics();
    }
}
