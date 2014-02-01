/*
 * Copyright 2014 Adam Dubiel, Przemek Hertel.
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
package org.smartparam.engine.index;

import org.smartparam.engine.core.matcher.Matcher;
import org.smartparam.engine.core.type.Type;

/**
 *
 * @author Adam Dubiel
 */
public class IndexLevelDescriptor {

    private final String name;

    private final Matcher effectiveMatcher;

    private final Matcher originalMatcher;

    private final String originalMatcherCode;

    private final boolean greedy;

    private final Type<?> type;

    public IndexLevelDescriptor(String name,
            Matcher effectiveMatcher, Matcher originalMatcher,
            String originalMatcherCode, boolean greedy, Type<?> type) {
        this.name = name;
        this.effectiveMatcher = effectiveMatcher;
        this.originalMatcher = originalMatcher;
        this.originalMatcherCode = originalMatcherCode;
        this.greedy = greedy;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public boolean greedy() {
        return greedy;
    }

    public Matcher effectiveMatcher() {
        return effectiveMatcher != null ? effectiveMatcher : originalMatcher;
    }

    public Matcher originalMatcher() {
        return originalMatcher;
    }

    public String originalMatcherCode() {
        return originalMatcherCode;
    }

    public Type<?> type() {
        return type;
    }

}