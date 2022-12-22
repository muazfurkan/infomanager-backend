package com.example.infomanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserCriteriaRepository {

    @Autowired
    private final EntityManager entityManager;

    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public UserCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<User> findAllWithFilters(UserPage userPage,
                                         UserSearchCriteria userSearchCriteria){
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = getPredicate(userSearchCriteria, userRoot);
        criteriaQuery.where(predicate);
        setOrder(userPage, criteriaQuery, userRoot);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(userPage.getPageNumber() * userPage.getPageSize());
        typedQuery.setMaxResults(userPage.getPageSize());

        Pageable pageable = getPageable(userPage);
        System.out.println(typedQuery.getResultList());
//        long usersCount = getUsersCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, 10);
//        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
    }

    private Predicate getPredicate(UserSearchCriteria userSearchCriteria, Root<User> userRoot){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(userSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("name"),
                            "%" + userSearchCriteria.getName().toLowerCase() + "%")
            );
        }
        if(Objects.nonNull(userSearchCriteria.getEmail())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("email"),
                            "%" + userSearchCriteria.getEmail().toLowerCase() + "%")
                    );
        }
        if(Objects.nonNull(userSearchCriteria.getPhone())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("phone"),
                            "%" + userSearchCriteria.getPhone() + "%")
                    );
        }
        if(Objects.nonNull(userSearchCriteria.getAddress())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("address"),
                            "%" + userSearchCriteria.getAddress().toLowerCase() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(UserPage userPage,
                          CriteriaQuery<User> criteriaQuery,
                          Root<User> userRoot){
        if(userPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(userPage.getSortBy())));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(userPage.getSortBy())));
        }
    }

    private Pageable getPageable(UserPage userPage){
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        return PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
    }

//    private long getUsersCount(Predicate predicate){
//        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
//        Root<User> countRoot = countQuery.from(User.class);
//        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
//        TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery);
//        System.out.println(typedQuery.getSingleResult());
//        System.out.println("2");
//        System.out.println("2" + entityManager.createQuery(countQuery).getFirstResult());
//        System.out.println("3" + entityManager.createQuery(countQuery).getMaxResults());
//        System.out.println("1" + entityManager.createQuery(countQuery).getResultStream());
//
//        return entityManager.createQuery(countQuery).getSingleResult();
//    }
}
