package com.example.infomanager.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @NotNull
    public String adminName;

    @NotNull
    public String password;
}
