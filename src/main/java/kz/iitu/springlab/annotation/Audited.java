
package kz.iitu.springlab.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audited {

    String action();

    boolean logArguments() default false;
}
