package com.example;

public class Main {
    public static void main(String[] args) {
        User user1 = new User(1);
        User user2 = new User(2);
        Book book1 = new Book();
        book1.setAuthor(user1);
        book1.setPublished(true);
        Book book2 = new Book();

        assert user1.can(Book.Actions.READ.name(), book1);
        assert user2.can(Book.Actions.READ.name(), book1);
        assert user1.can(Book.Actions.EDIT.name(), book1);
        assert !user2.can(Book.Actions.EDIT.name(), book1);
        assert !user2.can(Book.Actions.READ.name(), book2);
        assert !user2.can(Book.Actions.READ.name(), book2);

        assert user1.canReadBook(book1);
    }
}
