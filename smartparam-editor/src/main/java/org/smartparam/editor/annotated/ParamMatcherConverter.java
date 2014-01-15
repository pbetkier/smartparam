/*
 * Copyright 2014 Adam Dubiel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.editor.annotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.smartparam.engine.annotated.annotations.ObjectInstance;

/**
 *
 * @author Adam Dubiel
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamMatcherConverter {

    /**
     * Name of matcher this converter supports.
     *
     * @return name
     */
    String value();

    /**
     * Returns array of matcher names that this converter supports.
     *
     * @return names
     */
    String[] values() default {};

    /**
     * Compatibility option, should not be used.
     */
    ObjectInstance[] instances() default {};
}
