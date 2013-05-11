package org.smartparam.serializer.model;

import java.util.List;
import java.util.Set;
import org.smartparam.engine.model.Level;
import org.smartparam.engine.model.Parameter;
import org.smartparam.engine.model.ParameterEntry;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
public interface EditableParameter extends Parameter {

    void setName(String name);
    
    void setLevels(List<Level> levels);
    
    void setInputLevels(int inputLevels);
    
    void setEntries(Set<ParameterEntry> entries);
    
    void setCacheable(boolean cacheable);
    
    void setNullable(boolean nullable);

}
