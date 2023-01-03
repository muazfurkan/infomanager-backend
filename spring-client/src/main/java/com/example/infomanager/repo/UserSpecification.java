package com.example.infomanager.repo;

import com.example.infomanager.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserSpecification{

    public static Specification<User> getSpec(String filterText, String criteria){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.equals(criteria, "name")){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filterText + "%"));
            }
            if(Objects.equals(criteria, "email")){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + filterText + "%"));
            }
            if(Objects.equals(criteria, "phone")){
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + filterText + "%"));
            }
            if(Objects.equals(criteria, "address")){
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + filterText + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
