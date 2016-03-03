package com.zero.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by zero on 16/3/2.
 */
@Target(value = {java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE })
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface NeedLogin {
}
