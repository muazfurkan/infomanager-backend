package com.example.infomanager.controller;

import com.example.infomanager.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenGenerator;

    @PostMapping(path = "/token")
    @ResponseBody
    public String getToken(@RequestBody String username, String password){
        return jwtTokenGenerator.generateToken(username, password);
    }

}
