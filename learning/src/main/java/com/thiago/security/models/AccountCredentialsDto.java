package com.thiago.security.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountCredentialsDto extends RepresentationModel<AccountCredentialsDto> {
    
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
