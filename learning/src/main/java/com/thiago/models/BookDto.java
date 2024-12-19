package com.thiago.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@With
public class BookDto extends RepresentationModel<BookDto> {
    
    @JsonProperty("id")
    private Long key;
    
    @NotEmpty
    private String author;
    
    @NotNull
    private Date launch_date;
    
    @NotNull
    private BigDecimal price;
    
    @NotEmpty
    private String title;
}
