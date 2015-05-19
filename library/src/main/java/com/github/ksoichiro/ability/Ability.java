package com.github.ksoichiro.ability;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @author Soichiro Kashima
 */
public class Ability implements Serializable {
    private static final long serialVersionUID = 1;

    private ArrayList<Class<?>> rules;
    private Rule selectedRule;
    private boolean nullObjectAllowed;

    // Instantiate rule classes after deserialization
    // not to force rule classes to implement Serializable
    private transient Map<Class<?>, Rule> ruleInstances;

    public Ability() {
        rules = new ArrayList<Class<?>>();
        ruleInstances = new HashMap<Class<?>, Rule>();
        nullObjectAllowed = false;
    }

    public void addRule(Class<?> ruleClass) {
        if (!Rule.class.isAssignableFrom(ruleClass)) {
            throw new IllegalArgumentException("Rule class must implement 'Rule' interface");
        }
        if (!rules.contains(ruleClass)) {
            rules.add(ruleClass);
            ruleInstances.put(ruleClass, newRuleInstance(ruleClass));
        }
    }

    public void useRule(Class<?> ruleClass) {
        if (!rules.contains(ruleClass)) {
            throw new IllegalArgumentException("Unregistered rule class is used: " + ruleClass.getCanonicalName());
        }
        selectedRule = newRuleInstance(ruleClass);
    }

    public void resetUseRule() {
        selectedRule = null;
    }

    public boolean hasRule(Class<?> ruleClass) {
        return rules.contains(ruleClass);
    }

    public void setNullObjectAllowed(boolean nullObjectAllowed) {
        this.nullObjectAllowed = nullObjectAllowed;
    }

    public boolean allowed(Object object, String action, Object subject) {
        if (!nullObjectAllowed) {
            return false;
        }
        Set<String> rules = new HashSet<String>();
        HashMap<Class<?>, Rule> validRules = new HashMap<Class<?>, Rule>();
        if (selectedRule != null) {
            validRules.put(selectedRule.getClass(), selectedRule);
        } else {
            validRules.putAll(ruleInstances);
        }
        for (Rule rule : validRules.values()) {
            Set<String> r = rule.allowed(object, subject);
            if (r != null && !r.isEmpty()) {
                rules.addAll(r);
            }
        }
        return rules.contains(action);
    }

    private Rule newRuleInstance(Class<?> ruleClass) {
        try {
            return (Rule) ruleClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate the rule class. "
                    + "Check if the rule class defines the default constructor and "
                    + "implements Rule interface");
        }
    }

    private void restoreRuleInstances() {
        ruleInstances = new HashMap<Class<?>, Rule>();
        for (Class<?> ruleClass : rules) {
            ruleInstances.put(ruleClass, newRuleInstance(ruleClass));
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        restoreRuleInstances();
    }
}
