package com.example.infomanager.specification;

import com.example.infomanager.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<User, String> {

    List<User> findAll(Specification<User> specification);
}
