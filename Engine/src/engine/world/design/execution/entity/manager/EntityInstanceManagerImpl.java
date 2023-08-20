package engine.world.design.execution.entity.manager;

import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import com.sun.javaws.exceptions.InvalidArgumentException;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.entity.impl.EntityInstanceImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private int count;
    private final Map<Integer, EntityInstance> instances;
    List <Integer> instanceToKill = new ArrayList<>();
    public EntityInstanceManagerImpl() {
        count = 0;
        instances = new HashMap<>();
    }

    @Override
    public EntityInstanceManagerDTO createDTO() {
        Map<Integer, EntityInstanceDTO> instanceDTOMapToId = new HashMap<>();
        instances.forEach(((Id, entityInstance) -> instanceDTOMapToId.put(Id,entityInstance.createDTO())));
        return new EntityInstanceManagerDTO(instanceDTOMapToId);
    }

    @Override
    public EntityInstance create(EntityDefinition entityDefinition) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
        instances.put(count, newEntityInstance);

        for (PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }

        return newEntityInstance;
    }

    @Override
    public Map<Integer, EntityInstance> getInstances() {
        return instances;
    }

    private void addEntityToDieList(int id) {
        instanceToKill.add(id);
    }

    @Override
    public void killEntities() {
        instanceToKill.forEach(id -> instances.remove(id));
        instanceToKill = new ArrayList<>();
    }

    @Override
    public void killEntity(int id) {
        if(instances.containsKey(id)) {
            addEntityToDieList(id);
        }
        else {
            throw new IllegalArgumentException("this instance is already dead");
        }
    }
}