package com.example.infomanager.repo;

import com.example.infomanager.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<User, String> {

    List<User> findAll(Specification<User> specification);
}
