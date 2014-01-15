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
package org.smartparam.editor.config;

import java.util.Arrays;
import org.smartparam.editor.core.matcher.MatcherAwareConverter;
import org.smartparam.editor.core.store.ParamRepositoryNaming;
import org.smartparam.engine.annotated.PackageList;
import org.smartparam.engine.annotated.initialization.MethodScannerInitializer;
import org.smartparam.engine.annotated.initialization.TypeScannerInitializer;
import org.smartparam.engine.config.ParamEngineConfig;
import org.smartparam.engine.config.ParamEngineConfigBuilder;
import org.smartparam.engine.config.initialization.ComponentInitializer;
import org.smartparam.engine.config.initialization.ComponentInitializerRunner;
import org.smartparam.engine.config.initialization.PostConstructInitializer;
import org.smartparam.engine.config.pico.ComponentDefinition;
import org.smartparam.engine.core.function.FunctionInvoker;

import static org.smartparam.engine.config.pico.ComponentDefinition.component;

/**
 *
 * @author Adam Dubiel
 */
public final class ParamEditorConfigBuilder {

    private final ParamEditorConfig config = new ParamEditorConfig();

    private final PackageList packageList;

    private boolean annotationScanEnabled = true;

    private ParamEditorConfigBuilder() {
        packageList = new PackageList("org.smartparam.editor");
    }

    public static ParamEditorConfigBuilder paramEditorConfig() {
        return new ParamEditorConfigBuilder();
    }

    public ParamEditorConfig build() {
        if (annotationScanEnabled) {
            withComponentInitializers(new TypeScannerInitializer(packageList), new MethodScannerInitializer(packageList));
        }
        withComponentInitializers(new PostConstructInitializer());
        return config;
    }

    /**
     * Disable annotation scanning, this will return bare ParamEngine without
     * any default components.
     */
    public ParamEditorConfigBuilder withAnnotationScanDisabled() {
        annotationScanEnabled = false;
        return this;
    }

    /**
     * Add packages (including all descendants) that will be scanned in search of
     * SmartParam annotations. By default SmartParam only scans default package
     * (org.smartparam.editor) which provides a base set of capabilities. It is
     * also possible to disable annotation scanning by using {@link #withAnnotationScanDisabled() }.
     */
    public ParamEditorConfigBuilder withPackagesToScan(String... packagesToScan) {
        packageList.addAll(packagesToScan);
        return this;
    }

    /**
     * Add packages that will be scanned in search of SmartParam annotations. By default
     * SmartParam only scans default package (org.smartparam.editor) which provides a
     * base set of capabilities. It is also possible to disable annotation scanning
     * by using {@link #withAnnotationScanDisabled() }.
     */
    public ParamEditorConfigBuilder withPackagesToScan(PackageList packagesToScan) {
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
    public ParamEditorConfigBuilder withComponent(Class<?> interfaceClass, Object component) {
        config.addComponent(ComponentDefinition.component(interfaceClass, component));
        return this;
    }

    public ParamEditorConfigBuilder withRepositoryNaming(ParamRepositoryNaming repositoryNaming) {
        config.addComponent(component(ParamRepositoryNaming.class, repositoryNaming));
        return this;
    }

    /**
     * Register custom implementation of initialization runner. This goes deep
     * into ParamEditor construction process, so watch out.
     */
    public ParamEditorConfigBuilder withInitializationRunner(ComponentInitializerRunner runner) {
        config.setInitializationRunner(runner);
        return this;
    }

    /**
     * Register additional {@link ComponentInitializer}. These are useful if custom
     * component needs custom initialization (and {@link org.smartparam.engine.config.initialization.PostConstructInitializer}
     * is not enough.
     */
    public ParamEditorConfigBuilder withComponentInitializers(ComponentInitializer... initializers) {
        config.addComponentInitializers(Arrays.asList(initializers));
        return this;
    }

    /**
     * Register {@link MatcherAwareConverter}.
     */
    public ParamEditorConfigBuilder withMatcherConverter(String functionType, MatcherAwareConverter matcherConverter) {
        config.addMatcherConverter(functionType, matcherConverter);
        return this;
    }

}
