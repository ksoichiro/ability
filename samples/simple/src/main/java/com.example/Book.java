package com.example;

import com.github.ksoichiro.ability.Rule;

import java.util.HashSet;
import java.util.Set;

public class Book implements Rule {
    enum Actions {
        READ,
        EDIT,
    }

    private boolean published;

    private User author;

    @Override
    public Set<String> allowed(Object object, Object subject) {
        Set<String> rules = new HashSet<String>();
        if (subject == null || !(subject instanceof Book) || object == null || !(object instanceof User)) {
            return rules;
        }
        User user = (User) object;
        Book book = (Book) subject;
        if (book.published) {
            rules.add(Actions.READ.name());
        }
        if (book.author != null && book.author.getId() == user.getId()) {
            rules.add(Actions.EDIT.name());
        }
        return rules;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}