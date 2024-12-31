package com.thiago.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@With
public class PersonDto extends RepresentationModel<PersonDto> {
    @JsonProperty("id")
    private Long key;
    @JsonProperty("first_name")
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private Boolean enabled;
}
