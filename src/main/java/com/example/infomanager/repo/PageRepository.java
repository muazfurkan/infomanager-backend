package com.example.infomanager.repo;

import com.example.infomanager.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends PagingAndSortingRepository<User, Long> {}
