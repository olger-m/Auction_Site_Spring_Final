package com.sda.auction_site.service;


import com.sda.auction_site.model.Role;
import com.sda.auction_site.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role saveRole(Role role){

        return roleRepository.save(role);
    }
    public Role getByRoleName(String name){

        return roleRepository.findByRoleName(name).orElseThrow(() -> new RuntimeException("Role with name " + name + " not found "));
    }
}
