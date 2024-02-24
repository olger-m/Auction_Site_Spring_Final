package com.sda.auction_site.repository;

import com.sda.auction_site.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {



    Optional<Role> findByRoleName(String name);
}
