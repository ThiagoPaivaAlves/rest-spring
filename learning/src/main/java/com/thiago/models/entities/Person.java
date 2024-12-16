package com.thiago.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="address")
    private String address;

    @Column(name="gender")
    private String gender;
}
