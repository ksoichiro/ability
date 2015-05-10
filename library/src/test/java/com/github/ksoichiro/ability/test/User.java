package com.github.ksoichiro.ability.test;

import com.github.ksoichiro.ability.Ability;

public class User {
    private Ability ability;
    private long id;

    public User() {
        ability = new Ability();
        ability.addRule(Book.class);
    }

    public User(long id) {
        this();
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean can(String action, Object subject) {
        return ability.allowed(this, action, subject);
    }

    public boolean canReadBook(Book subject) {
        return ability.allowed(this, Book.Actions.READ.name(), subject);
    }
}
