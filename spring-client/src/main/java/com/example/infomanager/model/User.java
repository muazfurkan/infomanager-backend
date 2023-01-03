package com.example.infomanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull("Name is required!")
    @Column(nullable = false)
    private String name;

    @NotNull("Email is required!")
    @Email
    @Column(nullable = false)
    private String email;

    private String phone;

    private String address;

}