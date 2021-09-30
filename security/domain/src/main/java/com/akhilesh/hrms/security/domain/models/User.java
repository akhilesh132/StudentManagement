package com.akhilesh.hrms.security.domain.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public final class User {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        if (username == null) {
            throw new IllegalArgumentException("username can't be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("password can't be null");
        }
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public User withPassword(Password password) {
        return new User(this.username, password);
    }

    public UserDetails getUserDetails() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return password.value();
            }

            @Override
            public String getUsername() {
                return username.value();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
