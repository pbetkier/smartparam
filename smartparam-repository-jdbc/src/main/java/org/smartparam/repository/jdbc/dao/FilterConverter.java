/*
 * Copyright 2013 Adam Dubiel.
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
package org.smartparam.repository.jdbc.dao;

import org.polyjdbc.core.query.Order;
import org.smartparam.editor.viewer.SortDirection;

/**
 *
 * @author Adam Dubiel
 */
public final class FilterConverter {

    private FilterConverter() {
    }

    public static String parseAntMatcher(String matcher) {
        return matcher.replaceAll("\\*", "%").toUpperCase();
    }

    public static Order parseSortOrder(SortDirection sortOrder) {
        return sortOrder == SortDirection.ASC ? Order.ASC : Order.DESC;
    }
}
