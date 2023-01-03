package com.example.infomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Entity
@Table(name = "admin")
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull("userName is required!")
    @Column(nullable = false)
    private String adminName;

    @NotNull("Password is required!")
    @Column(nullable = false)
    private String password;

    private String role;
    private boolean enabled = false;

}
