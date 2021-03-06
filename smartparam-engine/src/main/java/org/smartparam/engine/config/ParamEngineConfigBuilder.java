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

import org.smartparam.engine.config.initialization.ComponentInitializer;
import org.smartparam.engine.config.initialization.ComponentInitializerRunner;
import java.util.Arrays;
import org.smartparam.engine.annotated.PackageList;
import org.smartparam.engine.annotated.RepositoryObjectKey;
import org.smartparam.engine.annotated.initialization.MethodScannerInitializer;
import org.smartparam.engine.config.initialization.PostConstructInitializer;
import org.smartparam.engine.annotated.initialization.TypeScannerInitializer;
import org.smartparam.engine.core.function.FunctionCache;
import org.smartparam.engine.core.matcher.Matcher;
import org.smartparam.engine.core.function.FunctionInvoker;
import org.smartparam.engine.core.function.FunctionRepository;
import org.smartparam.engine.core.parameter.ParamRepository;
import org.smartparam.engine.core.prepared.PreparedParamCache;
import org.smartparam.engine.core.type.Type;

/**
 * ParamEngine configuration builder. Call {@link #build() } to create
 * immutable configuration object that should be used to construct
 * new ParamEngine instance using {@link ParamEngineFactory}.
 *
 * Remember, that instead of registering components by hand you can use
 * annotation scanning (configured via {@link #withAnnotationScanEnabled(java.lang.String...) }
 * to discover classes.
 *
 * @author Adam Dubiel
 */
public final class ParamEngineConfigBuilder {

    private final ParamEngineConfig paramEngineConfig;

    private boolean annotationScanEnabled = true;

    private final PackageList packageList = new PackageList();

    private ParamEngineConfigBuilder() {
        paramEngineConfig = new ParamEngineConfig();
    }

    /**
     * Start building configuration.
     */
    public static ParamEngineConfigBuilder paramEngineConfig() {
        return new ParamEngineConfigBuilder();
    }

    /**
     * Finalizes configuration building and returns immutable config object.
     */
    public ParamEngineConfig build() {
        if (annotationScanEnabled) {
            withComponentInitializers(new TypeScannerInitializer(packageList), new MethodScannerInitializer(packageList));
        }
        withComponentInitializers(new PostConstructInitializer());
        return paramEngineConfig;
    }

    /**
     * Disable annotation scanning, this will return bare ParamEngine without
     * any default components.
     */
    public ParamEngineConfigBuilder withAnnotationScanDisabled() {
        annotationScanEnabled = false;
        return this;
    }

    /**
     * Add packages (including all descendants) that will be scanned in search of
     * SmartParam annotations. By default SmartParam only scans default package
     * (org.smartparam.engine) which provides a  base set of capabilities. It is
     * also possible to disable annotation scanning by using {@link #withAnnotationScanDisabled() }.
     */
    public ParamEngineConfigBuilder withPackagesToScan(String... packagesToScan) {
        packageList.addAll(packagesToScan);
        return this;
    }

    /**
     * Add packages that will be scanned in search of SmartParam annotations. By default
     * SmartParam only scans default package (org.smartparam.engine) which provides a
     * base set of capabilities. It is also possible to disable annotation scanning
     * by using {@link #withAnnotationScanDisabled() }.
     */
    public ParamEngineConfigBuilder withPackagesToScan(PackageList packagesToScan) {
        this.packageList.addAll(packagesToScan.getPackages());
        return this;
    }

    /**
     * Replace default ParamEngine components with custom one. This method should
     * be used when you want to go deeper and replace one of ParamEngine core
     * interfaces. If so, register object instance that implements interface of
     * component you want to replace. For details on what are interfaces and
     * classes, please take a look at source code of {@link ParamEngineConfig#injectDefaults(java.util.List) }.
     */
    public ParamEngineConfigBuilder withComponent(Object component) {
        paramEngineConfig.addComponent(component);
        return this;
    }

    /**
     * Add a self-registering module that encapsulates all self configuration.
     */
    public ParamEngineConfigBuilder registerModule(ParamEngineModule module) {
        module.registerSelf(this);
        return this;
    }

    /**
     * Register parameter repositories. There has to be at least one registered.
     * Order of registration matters, as it determines order of querying. First
     * repository that contains parameter wins.
     */
    public ParamEngineConfigBuilder withParameterRepositories(ParamRepository... repositories) {
        paramEngineConfig.addParameterRepositories(Arrays.asList(repositories));
        return this;
    }

    /**
     * Register {@link FunctionRepository} with given order (default repository has
     * priority 100). Order might matter if you plan on overwriting functions
     * from one repository with function with the other. Highest order goes
     * last.
     */
    public ParamEngineConfigBuilder withFunctionRepository(String functionType, int order, FunctionRepository repository) {
        paramEngineConfig.addFunctionRepository(new RepositoryObjectKey(functionType, order), repository);
        return this;
    }

    /**
     * Register {@link FunctionInvoker}.
     */
    public ParamEngineConfigBuilder withFunctionInvoker(String functionType, FunctionInvoker invoker) {
        paramEngineConfig.addFunctionInvoker(functionType, invoker);
        return this;
    }

    /**
     * Register {@link Type} under code.
     */
    public ParamEngineConfigBuilder withType(String code, Type<?> type) {
        paramEngineConfig.addType(code, type);
        return this;
    }

    /**
     * Register {@link Matcher} under code.
     */
    public ParamEngineConfigBuilder withMatcher(String code, Matcher matcher) {
        paramEngineConfig.addMatcher(code, matcher);
        return this;
    }

    /**
     * Register custom {@link FunctionCache}.
     */
    public ParamEngineConfigBuilder withFunctionCache(FunctionCache functionCache) {
        paramEngineConfig.setFunctionCache(functionCache);
        return this;
    }

    /**
     * Register custom {@link ParameterCache}.
     */
    public ParamEngineConfigBuilder withParameterCache(PreparedParamCache parameterCache) {
        paramEngineConfig.setParameterCache(parameterCache);
        return this;
    }

    /**
     * Register custom implementation of initialization runner. This goes deep
     * into ParamEngine construction process, so watch out.
     */
    public ParamEngineConfigBuilder withInitializationRunner(ComponentInitializerRunner runner) {
        paramEngineConfig.setInitializationRunner(runner);
        return this;
    }

    /**
     * Register additional {@link ComponentInitializer}. These are useful if custom
     * component needs custom initialization (and {@link org.smartparam.engine.config.initialization.PostConstructInitializer}
     * is not enough.
     */
    public ParamEngineConfigBuilder withComponentInitializers(ComponentInitializer... initializers) {
        paramEngineConfig.addComponentInitializers(Arrays.asList(initializers));
        return this;
    }
}
