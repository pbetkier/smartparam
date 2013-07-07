package org.smartparam.engine.test.assertions;

import org.fest.assertions.api.AbstractAssert;
import org.smartparam.engine.core.engine.ParamEngine;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
public class ParamEngineAssert extends AbstractAssert<ParamEngineAssert, ParamEngine> {

    public ParamEngineAssert(ParamEngine actual) {
        super(actual, ParamEngineAssert.class);
    }

    public static ParamEngineAssert assertThat(ParamEngine actual) {
        return new ParamEngineAssert(actual);
    }

    public ParamEngineAssert hasInitializedTree() {
        Assertions.assertThat(actual.getParamPreparer()).isNotNull();
        Assertions.assertThat(actual.getParamPreparer().getMatcherRepository()).isNotNull();
        Assertions.assertThat(actual.getParamPreparer().getTypeRepository()).isNotNull();
        Assertions.assertThat(actual.getParamPreparer().getParamCache()).isNotNull();

        Assertions.assertThat(actual.getFunctionManager()).isNotNull();
        Assertions.assertThat(actual.getFunctionManager().getFunctionProvider()).isNotNull();
        Assertions.assertThat(actual.getFunctionManager().getFunctionProvider().getFunctionCache()).isNotNull();
        Assertions.assertThat(actual.getFunctionManager().getInvokerRepository()).isNotNull();

        return this;
    }

    public ParamEngineAssert hasInitializedTreeWithScannedItems() {
        hasInitializedTree();

        Assertions.assertThat(actual.getFunctionManager().getInvokerRepository()).hasItems();
        Assertions.assertThat(actual.getFunctionManager().getFunctionProvider()).hasItems();
        Assertions.assertThat(actual.getParamPreparer().getTypeRepository()).hasItems();
        Assertions.assertThat(actual.getParamPreparer().getMatcherRepository()).hasItems();

        return this;
    }
}