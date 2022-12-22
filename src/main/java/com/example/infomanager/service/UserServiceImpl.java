package com.example.infomanager.service;

import com.example.infomanager.*;
import com.example.infomanager.specification.SpecificationRepository;
import com.example.infomanager.specification.UserSpecification;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PageRepository pageRepository;

    private final UserCriteriaRepository userCriteriaRepository;

    private final SpecificationRepository specificationRepository;

    @Override
    public List<UserDTO> getUsersDTO(){
        List<User> userList = (List<User>) userRepository.findAll();
        return userList.stream().map(this::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Page<User> findAll(String filterText, String criteria, UserPage userPage){
        Specification<User> specification = UserSpecification.getSpec(filterText, criteria);
        Pageable pageable = getPageable(userPage);
        return new PageImpl<>(specificationRepository.findAll(specification), pageable, 10);
    }

    @Override
    public Page<User> getPage(Integer currentPage, Integer pageSize){
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return pageRepository.findAll(pageable);
    }

    @Override
    public Page<User> getUsersByCriteria(UserPage userPage,
                               UserSearchCriteria userSearchCriteria){
        return userCriteriaRepository.findAllWithFilters(userPage, userSearchCriteria);
    }

    @Override
    public User addUser(User user){
        return userRepository.save(user);
    }

    public User deleteUser(Long id){
        userRepository.deleteById(id);
        return null;
    }

    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public User editUser(Long id, User userDetails){
        User currentUser = userRepository.findById(id).orElseThrow();
        currentUser.setName(userDetails.getName());
        currentUser.setEmail(userDetails.getEmail());
        currentUser.setPhone(userDetails.getPhone());
        currentUser.setAddress(userDetails.getAddress());
        userRepository.save(currentUser);
        return currentUser;
    }

    public UserDTO fromEntityToDTO(User user){
        return new UserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
    }

    private Pageable getPageable(UserPage userPage){
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        return PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
    }


}
