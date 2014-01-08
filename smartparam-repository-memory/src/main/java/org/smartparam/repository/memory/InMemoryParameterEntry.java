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
package org.smartparam.repository.memory;

import java.util.Arrays;
import org.smartparam.editor.model.EditableParameterEntry;
import org.smartparam.editor.model.ParameterEntryKey;
import org.smartparam.engine.core.parameter.ParameterEntry;

/**
 *
 * @author Adam Dubiel
 */
public class InMemoryParameterEntry implements EditableParameterEntry {

    private static final String[] EMPTY_LEVELS = {};

    private final InMemoryParameterEntryKey key;

    private String[] levels = EMPTY_LEVELS;

    InMemoryParameterEntry() {
        this.key = new InMemoryParameterEntryKey();
    }

    InMemoryParameterEntry(ParameterEntry entry) {
        this();
        merge(entry);
    }

    final void merge(ParameterEntry entry) {
        this.levels = getLevels();
    }

    @Override
    public ParameterEntryKey getKey() {
        return key;
    }

    InMemoryParameterEntryKey getRawKey() {
        return key;
    }

    @Override
    public String[] getLevels() {
        return Arrays.copyOf(levels, levels.length);
    }

}