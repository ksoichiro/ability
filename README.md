# ability

Simple authorization library for Java.

## Usage

### Create rules

Model class implements `Rule` interface and implements `allowed` method.

```java
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
    // Getters and setters are omitted
}
```

### Add rules

User class has `Ability` instance and add some authorization methods.

```java
public class User {
    private Ability ability;
    public User() {
        ability = new Ability();
        ability.addRule(Book.class);
    }

    public boolean can(String action, Object subject) {
        return ability.allowed(this, action, subject);
    }

    public boolean canReadBook(Book subject) {
        return ability.allowed(this, Book.Actions.READ.name(), subject);
    }
    // Other unimportant codes are omitted
}
```

### Use

```java
User user1 = new User(1);
User user2 = new User(2);
Book book1 = new Book();
book1.setAuthor(user1);
book1.setPublished(true);
Book book2 = new Book();

assertTrue(user1.canReadBook(book1));

assertTrue(user1.can(Book.Actions.EDIT.name(), book1));
assertFalse(user2.can(Book.Actions.EDIT.name(), book1));

// Or you can directly use it
Ability ability = new Ability();
ability.addRule(Book.class);
assertTrue(ability.allowed(user1, Book.Actions.READ.name(), book1));
```

## Thanks

* Inspired by [Six](https://github.com/randx/six), which is used in GitLab

## License

    Copyright 2015 Soichiro Kashima

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
