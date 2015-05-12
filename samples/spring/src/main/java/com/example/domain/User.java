package com.example.domain;

import com.github.ksoichiro.ability.Ability;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends org.springframework.security.core.userdetails.User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @ManyToOne(cascade = CascadeType.ALL)
    private User manager;

    @ManyToOne(cascade = CascadeType.ALL)
    private Organization organization;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Transient
    private Ability ability;

    public User() {
        super("INVALID", "INVALID", false, false, false, false, new ArrayList<GrantedAuthority>());
        initAbilities();
    }

    public User(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        initAbilities();
    }

    public boolean can(String action) {
        return can(action, null);
    }

    public boolean can(String action, Object subject) {
        return ability.allowed(this, action, subject);
    }

    private void initAbilities() {
        ability = new Ability();
        ability.addRule(Organization.class);
    }
}
