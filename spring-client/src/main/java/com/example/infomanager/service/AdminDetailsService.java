package com.example.infomanager.service;

import com.example.infomanager.model.Admin;
import com.example.infomanager.repo.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminName(adminName);
        if(admin == null){
            throw new UsernameNotFoundException("No admin found!");
        }
        return new org.springframework.security.core.userdetails.User(
            admin.getAdminName(),
            admin.getPassword(),
            admin.isEnabled(),
                true,
                true,
                true,
                getAuthorities(List.of(admin.getRole()))
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role: roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
