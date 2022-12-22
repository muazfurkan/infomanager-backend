package com.example.infomanager;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends PagingAndSortingRepository<User, Long> {}
