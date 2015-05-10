package com.github.ksoichiro.ability.test;

import com.github.ksoichiro.ability.Ability;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

public class AbilityTest {
    @Test
    public void empty() {
        Ability ability = new Ability();

        assertFalse(ability.allowed(new User(), Book.Actions.READ.name(), new Book()));
    }

    @Test
    public void oneRule() {
        Ability ability = new Ability();

        ability.addRule(Book.class);
        User user1 = new User(1);
        User user2 = new User(2);
        Book book1 = new Book();
        book1.setAuthor(user1);
        book1.setPublished(true);
        Book book2 = new Book();

        assertEquals(1, user1.getId());
        assertTrue(ability.allowed(user1, Book.Actions.READ.name(), book1));
        assertTrue(ability.allowed(user2, Book.Actions.READ.name(), book1));
        assertTrue(ability.allowed(user1, Book.Actions.EDIT.name(), book1));
        assertFalse(ability.allowed(user2, Book.Actions.EDIT.name(), book1));
        assertFalse(ability.allowed(user1, Book.Actions.READ.name(), book2));
        assertFalse(ability.allowed(user2, Book.Actions.READ.name(), book2));
    }

    @Test
    public void abilityIsSerializable() throws Exception {
        Ability ability = new Ability();
        ability.addRule(Book.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(ability);
        out.close();
        baos.close();
        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Ability deserialized = (Ability) input.readObject();
        User user1 = new User(1);
        Book book1 = new Book();
        book1.setPublished(true);

        assertTrue(ability.hasRule(Book.class));
        assertTrue(ability.allowed(user1, Book.Actions.READ.name(), book1));
        assertTrue(deserialized.hasRule(Book.class));
        assertTrue(deserialized.allowed(user1, Book.Actions.READ.name(), book1));
    }

    @Test
    public void userDefinedAbility() {
        User user1 = new User(1);
        User user2 = new User(2);
        Book book1 = new Book();
        book1.setAuthor(user1);
        book1.setPublished(true);
        Book book2 = new Book();

        assertEquals(1, user1.getId());
        assertTrue(user1.can(Book.Actions.READ.name(), book1));
        assertTrue(user2.can(Book.Actions.READ.name(), book1));
        assertTrue(user1.can(Book.Actions.EDIT.name(), book1));
        assertFalse(user2.can(Book.Actions.EDIT.name(), book1));
        assertFalse(user2.can(Book.Actions.READ.name(), book2));
        assertFalse(user2.can(Book.Actions.READ.name(), book2));

        assertTrue(user1.canReadBook(book1));
    }
}
