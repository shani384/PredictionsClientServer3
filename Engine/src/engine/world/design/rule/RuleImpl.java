package engine.world.design.rule;

import engine.world.design.action.api.Action;
import engine.world.design.rule.activation.api.Activation;
import engine.world.design.rule.activation.impl.ActivationImpl;

import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements Rule{

    private final String name;
    private Activation activation;
    private final List<Action> actions;

    public RuleImpl(String name,Activation activation) {
        this.name = name;
        this.activation = activation;
        this.actions = new ArrayList<>();
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Activation getActivation() {
        return activation;
    }

    @Override
    public List<Action> getActionToPreform() {
        return actions;
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }
}