package com.github.ksoichiro.ability;

import java.util.Set;

/**
 * @author Soichiro Kashima
 */
public interface Rule {
    Set<String> allowed(Object object, Object subject);
}
