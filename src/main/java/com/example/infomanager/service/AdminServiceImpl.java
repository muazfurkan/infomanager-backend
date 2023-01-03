package com.example.infomanager.service;

import com.example.infomanager.model.*;
import com.example.infomanager.repo.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;

//    private final UserCriteriaRepository userCriteriaRepository;
//
//    private final SpecificationRepository specificationRepository;

    @Override
    public Boolean checkAdmin(String adminName, String password){

    }

    @Override
    public Admin addAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    @Override
    public Admin deleteAdmin(Long id){
        adminRepository.deleteById(id);
        return null;
    }

    @Override
    public Admin editAdmin(Long id, Admin adminDetails){
        Admin currentUser = adminRepository.findById(id).orElseThrow();
        currentUser.setAdminName(adminDetails.getAdminName());
        currentUser.setPassword(adminDetails.getPassword());
        adminRepository.save(currentUser);
        return currentUser;
    }

}
