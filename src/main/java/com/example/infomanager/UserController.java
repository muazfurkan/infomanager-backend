package com.example.infomanager;

import com.example.infomanager.service.UserService;
import com.example.infomanager.specification.SpecificationRepository;
import com.example.infomanager.specification.UserSpecification;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private SpecificationRepository specificationRepository;
//    @GetMapping("/users")
//    public List<UserDTO> getUsers() {
//        return userService.getUsersDTO();
//    }

//    @GetMapping("/users")
//    public List<User> getUsers2(){
//        return userService.getUsers();
//    }

    @GetMapping("/users")
    public Page<User> paginationUsers(Integer pageSize,
                                 Integer currentPage) {
        return userService.getPage(currentPage, pageSize);
    }

//    @GetMapping("/filtered")
//    public ResponseEntity<Page<User>> getUsersByCriteria(UserPage userPage,
//                                                         UserSearchCriteria userSearchCriteria){
//        return new ResponseEntity<>(userService.getUsersByCriteria(userPage, userSearchCriteria),
//                HttpStatus.OK);
//    }

    @GetMapping("/filtered")
    public Page<User> getUsersWithSpecifications(UserPage userPage, String filterText, String criteria){
//        Specification<User> specification = UserSpecification.getSpec(filterText, criteria);
//        return specificationRepository.findAll(specification);
        return userService.findAll(filterText, criteria, userPage);
    }

    @PostMapping(path = "/users/adduser")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User resultUser = userService.addUser(user);
        return null;
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public User deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping(path = "/users/edituser/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping(path = "/users/edituser/{id}")
    public User editUserById(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.editUser(id, userDetails);
    }

}