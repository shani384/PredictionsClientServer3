package engine.world.design.expression;

import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;

import java.util.Random;

public enum ExpressionType {

    FLOAT {
        @Override
        public Float evaluate(String expression, Context context) {
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        float envPropertyVal = PropertyType.FLOAT.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return (float) res;
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            else if(context.getPrimaryEntityInstance().getPropertyByName(expression) != null){
                return PropertyType.FLOAT.convert(context.getPrimaryEntityInstance().getPropertyByName(expression).getValue());
            }
            else{
                try{
                    float floatFreeVal = Float.parseFloat(expression);
                    return floatFreeVal;
                }
                catch (NumberFormatException e2){
                    //"Unable to convert the string to float"
                }
            }
            return null;
        }
    },
    DECIMAL{
        @Override
        public Integer evaluate(String expression, Context context) {
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        int envPropertyVal = PropertyType.DECIMAL.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return res;
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression); // TODO: 19/08/2023 throw
                return PropertyType.DECIMAL.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e) {
                try {
                    int freeVal = Integer.parseInt(expression);
                    return freeVal;
                } catch (NumberFormatException e2) {
                    throw e2;
                }
            }
        }
    }, 
    STRING{
        @Override
        public String evaluate(String expression, Context context) {// TODO: 15/08/2023
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        String envPropertyVal = PropertyType.STRING.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        throw new RuntimeException("This random function can't random String value");
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression); // TODO: 19/08/2023 throw
                return PropertyType.STRING.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e) {
                return expression;
            }
        }
    },
    BOOLEAN{
        @Override
        public Boolean evaluate(String expression, Context context) { // TODO: 15/08/2023
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        Boolean envPropertyVal = PropertyType.BOOLEAN.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        throw new RuntimeException("This random function can't random boolean value");
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression);
                return PropertyType.BOOLEAN.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e){
                try{
                    boolean freeVal = Boolean.parseBoolean(expression);
                    return freeVal;
                }
                catch (NumberFormatException e2) {
                    throw e2;
                }
            }
        }
    };
    public abstract <T> T evaluate(String expression, Context context);
}
