package com.thiago.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "books")
@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date launch_date;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column
    private String title;
}
