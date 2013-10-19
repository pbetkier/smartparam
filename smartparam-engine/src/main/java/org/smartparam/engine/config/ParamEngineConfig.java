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
package org.smartparam.engine.config;

import java.util.List;
import java.util.Map;
import org.smartparam.engine.bean.RepositoryObjectKey;
import org.smartparam.engine.core.index.Matcher;
import org.smartparam.engine.core.invoker.FunctionInvoker;
import org.smartparam.engine.core.repository.FunctionRepository;
import org.smartparam.engine.core.repository.ParamRepository;
import org.smartparam.engine.core.type.Type;

/**
 *
 * @author Adam Dubiel
 */
public interface ParamEngineConfig {

    List<ComponentInitializer> getComponentInitializers();

    Map<String, FunctionInvoker> getFunctionInvokers();

    Map<RepositoryObjectKey, FunctionRepository> getFunctionRepositories();

    ComponentInitializerRunner getInitializationRunner();

    Map<String, Matcher> getMatchers();

    List<ParamRepository> getParameterRepositories();

    Map<String, Type<?>> getTypes();

}
