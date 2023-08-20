package engine.world.design.world.api;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.WorldDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;
import engine.world.design.termination.api.Termination;

import java.util.List;
import java.util.Map;

public interface World {


    EntityDefinition getEntityDefinitionByName(String name);
    EnvVariablesManager getEnvVariables();
    Map<Integer, EntityInstanceManagerDTO> runSimulation(Map<String, Object> propertyNameToValueAsString);

    void setEntities(Map<String, EntityDefinition> entities);

    void setEnvVariablesManager(EnvVariablesManager envVariablesManager);

    void setRules(List<Rule> ruleList);
    void setTermination(Termination termination);

    Map<String, EntityDefinition> getNameToEntityDefinition();
    Termination getTermination();
    List<Rule> getRules();
    WorldDTO createWorldDTO();
}
