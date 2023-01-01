package com.example.infomanager.service;

import com.example.infomanager.model.User;
import com.example.infomanager.model.UserDTO;
import com.example.infomanager.model.UserPage;
import com.example.infomanager.model.UserSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getUsersDTO();

    Page<User> getPage(Integer currentPage, Integer pageSize);

    List<User> getUsers();

    Page<User> findAll(String filterText, String criteria, UserPage userPage);

    Optional<User> findById(Long id);

    User addUser(User user);

    User deleteUser(Long id);

    User editUser(Long id, User userDetails);

    Page<User> getUsersByCriteria(UserPage userPage,
                                       UserSearchCriteria userSearchCriteria);

}
