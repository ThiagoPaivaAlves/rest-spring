package com.thiago.security.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="permission")
@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Permission implements GrantedAuthority {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String description;
    
    @Override
    public String getAuthority() {
        return this.getDescription();
    }
}
