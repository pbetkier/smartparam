/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
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
package org.smartparam.engine.test.builder;

import org.smartparam.engine.model.ParameterEntry;
import org.smartparam.engine.model.editable.EditableParameterEntry;

/**
 *
 * @author Adam Dubiel
 */
public abstract class AbstractParameterEntryTestBuilder<T extends EditableParameterEntry, B extends AbstractParameterEntryTestBuilder<?, ?>> {

    protected T entry;

    protected AbstractParameterEntryTestBuilder(T entry) {
        this.entry = entry;
    }

    protected abstract B self();

    public ParameterEntry build() {
        return entry;
    }

    public B withLevels(String... levelValues) {
        entry.setLevels(levelValues);
        return self();
    }
}