package com.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})//元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
