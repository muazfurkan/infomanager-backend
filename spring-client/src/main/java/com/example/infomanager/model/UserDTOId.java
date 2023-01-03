package com.example.infomanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOId {

    public long id;

    @NotNull
    public String name;

    @NotNull
    public String email;

    public String phone;

    public String address;
}
