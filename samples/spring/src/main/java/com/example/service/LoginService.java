package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == username || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        User domainUser = userRepository.findByUsername(username);
        if (domainUser == null) {
            throw new UsernameNotFoundException("User not found for name: " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (domainUser.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(domainUser.getRole().getName()));
        }
        User user = new User(username, domainUser.getPassword(), domainUser.isEnabled(), authorities);
        user.setId(domainUser.getId());
        user.setCreatedAt(domainUser.getCreatedAt());
        user.setUpdatedAt(domainUser.getUpdatedAt());
        user.setRole(domainUser.getRole());
        return user;
    }
}
