package org.smartparam.engine.core.provider;

import org.smartparam.engine.core.type.Type;

/**
 *
 * @author Przemek Hertel
 * @author Adam Dubiel
 * @since 0.1.0
 */
public interface TypeProvider {

    public void registerType(String code, Type<?> type);

    public Type<?> getType(String code);
}
