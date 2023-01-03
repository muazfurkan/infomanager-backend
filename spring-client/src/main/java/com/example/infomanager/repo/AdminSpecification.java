package com.example.infomanager.repo;

import com.example.infomanager.model.Admin;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminSpecification {

    public static Specification<Admin> getSpec(String adminName, String password){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + adminName + "%"));
            predicates.add(criteriaBuilder.like(root.get("password"), "%" + password + "%"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
