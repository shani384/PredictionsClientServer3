package engine.world.design.action.condition;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultipleCondition implements Condition{

    private List<Condition> conditions;
    String logical;

    public MultipleCondition(String logical) {// TODO: 15/08/2023
        this.logical = logical;
        conditions = new ArrayList<>();
    }
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }
    @Override
    public boolean evaluate(Context context) {
        if (logical.equals("Or")){
            return conditions.stream().
                    anyMatch(condition -> condition.evaluate(context));
        }
        else if(logical.equals("And")){
            return conditions.stream().
                    allMatch(condition -> condition.evaluate(context));
        }
        else {
            throw new RuntimeException("invalid logical sign");
        }
    }
}
