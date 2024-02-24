package com.sda.auction_site.repository;

import com.sda.auction_site.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {

    AppUser findUserByEmail(String email);

    AppUser findByUsername(String username);


}
