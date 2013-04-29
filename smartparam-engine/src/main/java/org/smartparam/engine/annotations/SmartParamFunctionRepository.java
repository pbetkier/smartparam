package org.smartparam.engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.smartparam.engine.annotations.scanner.AnnotatedObjectsScanner;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SmartParamSortable
public @interface SmartParamFunctionRepository {

    String value();

    String[] values() default {};

    SmartParamObjectInstance[] instances() default {};

    int order() default AnnotatedObjectsScanner.DEFAULT_ORDER_VALUE;
}
