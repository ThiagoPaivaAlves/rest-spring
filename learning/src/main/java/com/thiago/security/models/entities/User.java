package com.thiago.security.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="user_name", unique=true)
    private String userName;
    
    @Column(name="full_name")
    private String fullName;
    
    @Column(name="password")
    private String password;
    
    @Column(name="account_non_expired")
    private Boolean accountNotExpired;
    
    @Column(name="account_non_locked")
    private Boolean accountNonLocker;
    
    @Column(name="credentials_non_expired")
    private Boolean credentialsNonExpired;
    
    @Column(name="enabled")
    private Boolean enabled;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<Permission> permissions;
    
    public List<String> getRoles() {
        return permissions.stream().map(Permission::getDescription).toList();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.userName;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNotExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocker;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
