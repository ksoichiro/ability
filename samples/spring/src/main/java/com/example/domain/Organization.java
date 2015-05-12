package com.example.domain;

import com.example.constants.Abilities;
import com.example.constants.Roles;
import com.github.ksoichiro.ability.Rule;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Organization implements Rule {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Override
    public Set<String> allowed(Object object, Object subject) {
        Set<String> rules = new HashSet<String>();
        if (object == null || !(object instanceof User)) {
            return rules;
        }
        User user = (User) object;
        if (user.getRole().getName().equals(Roles.ADMIN)) {
            rules.add(Abilities.READ_ORGANIZATION);
        }
        return rules;
    }
}
