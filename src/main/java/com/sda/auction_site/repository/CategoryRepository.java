package com.sda.auction_site.repository;

import com.sda.auction_site.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    Category findByName(String name);
}
