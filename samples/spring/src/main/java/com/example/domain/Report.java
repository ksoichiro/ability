package com.example.domain;

import com.example.constants.Abilities;
import com.github.ksoichiro.ability.Rule;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Report implements Rule {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private boolean submitted;

    private boolean authorized;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})
    private User creator;

    @Override
    public Set<String> allowed(Object object, Object subject) {
        Set<String> rules = new HashSet<String>();
        if (subject == null || !(subject instanceof Report) || object == null || !(object instanceof User)) {
            return rules;
        }
        User user = (User) object;
        Report report = (Report) subject;
        boolean isCreator = report.creator != null && report.creator.getId().equals(user.getId());
        boolean isManager = report.creator != null && report.creator.getManager() != null && user.getManager() != null && report.creator.getManager().getId().equals(user.getManager().getId());
        if (report.submitted || isCreator) {
            rules.add(Abilities.READ_REPORT);
        }
        if (isCreator) {
            rules.add(Abilities.EDIT_REPORT);
        }
        if (isManager) {
            rules.add(Abilities.AUTHORIZE_REPORT);
        }
        return rules;
    }
}
