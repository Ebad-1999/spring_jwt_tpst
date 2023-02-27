package com.tpst.service;

import com.tpst.domain.Role;
import com.tpst.domain.User;
import com.tpst.domain.enums.UserRole;
import com.tpst.dto.RegisterRequest;
import com.tpst.exception.ConflictException;
import com.tpst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void save(RegisterRequest registerRequest) {

        if (userRepository.existsByUSerName(registerRequest.getUserName())) {
            throw new ConflictException("UserName is already in use");
        }

        User newUser = new User();
        newUser.setUserName(registerRequest.getUserName());
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());

        // newUser.setPassword(registerRequest.getPassword());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        //default role of new user will be "Employee"
        Role role = roleService.getRoleByType(UserRole.ROLE_STUDENT);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);

        userRepository.save(newUser);
    }
}