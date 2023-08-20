package engine.world.design.execution.entity.impl;

import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.PropertyInstanceDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;

import java.util.HashMap;
import java.util.Map;

public class EntityInstanceImpl implements EntityInstance {

    private final EntityDefinition entityDefinition;
    private final int id;
    private final Map<String, PropertyInstance> properties;

    public EntityInstanceImpl(EntityDefinition entityDefinition, int id) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        if (!properties.containsKey(name)) {
            throw new IllegalArgumentException("for entity of type " + entityDefinition.getName() + " has no property named " + name);
        }

        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public EntityInstanceDTO createDTO() {
        Map<String, PropertyInstanceDTO> propertyInstanceToNameDTO = new HashMap<>();
        properties.forEach((name, propertyInstance) -> propertyInstanceToNameDTO.put(name,propertyInstance.createDTO()));
        return new EntityInstanceDTO(entityDefinition.createEntityDefinitionDTO(), id,propertyInstanceToNameDTO);
    }
}
