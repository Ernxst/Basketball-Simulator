package com.example.entities.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Builder
@Entity(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @NonNull
    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
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

    public boolean equals(User user) {
        return username.equals(user.getUsername());
    }
}
