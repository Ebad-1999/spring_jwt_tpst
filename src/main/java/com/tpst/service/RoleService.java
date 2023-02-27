package com.tpst.service;


import com.tpst.domain.Role;
import com.tpst.domain.enums.UserRole;
import com.tpst.exception.ResourceNotFoundException;
import com.tpst.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Role getRoleByType(UserRole roleType) {

        Role role=  roleRepository.findByName(roleType).orElseThrow(
                ()-> new ResourceNotFoundException("Role is not find"));
        return role;
    }
}
