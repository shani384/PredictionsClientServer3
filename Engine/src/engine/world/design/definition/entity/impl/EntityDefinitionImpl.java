package engine.world.design.definition.entity.impl;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.property.PropertyInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition, Cloneable{

    private final String name;
    private final int population;
    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String name, int population) {
        this.name = name;
        this.population = population;
        properties = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }
    @Override
    public EntityDefinitionDTO createEntityDefinitionDTO(){
        List<PropertyDefinitionDTO> propertiesDefinitionDTO = new ArrayList<>();
        for (PropertyDefinition propertyDefinition: getProps()){
            propertiesDefinitionDTO.add(propertyDefinition.createPropertyDefinitionDTO());
        }
        return new EntityDefinitionDTO(name,population,propertiesDefinitionDTO);
    }
    @Override
    public PropertyDefinition getPropertyByName(String name) {
        for (PropertyDefinition propertyDefinition: properties){
            if (propertyDefinition.getName().equals(name)){
                return propertyDefinition;
            }
        }
        throw new IllegalArgumentException("for entity of type " + getName() + " has no property named " + name);
    }
}
