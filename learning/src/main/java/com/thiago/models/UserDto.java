package com.thiago.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiago.security.models.entities.Permission;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
public class UserDto extends RepresentationModel<UserDto> {
    
    @JsonProperty("id")
    private Long key;
    @NotEmpty
    private String username;
    
    private String fullName;
    
//    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    private String password;
    
    private Boolean accountNotExpired;
    
    private Boolean accountNonLocker;
    
    private Boolean credentialsNonExpired;
    
    private Boolean enabled;
    
    private List<Permission> permissions;
}
