package com.sda.auction_site.service;

import com.sda.auction_site.model.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MockedDataService {

    private final RoleService roleService;

    private final UserService userService;

    private final PasswordEncoder encoder;

    private final CategoryService categoryService;

    private final AuctionService auctionService;

    private final AuctionItemService auctionItemService;
    @PostConstruct
    public void initFakeData(){


        try{
            Role admin = roleService.getByRoleName("ROLE_ADMIN");

            System.out.println("initial roles exist skipping...");
        }catch(Exception e){

            System.out.println("initial roles not found ..starting insert...");

            Role admin = new Role(0L, "ROLE_ADMIN");
            Role user = new Role(0L, "ROLE_USER");
            List.of(admin,user).forEach(role -> {
                roleService.saveRole(role);

                System.out.println("inserted role " + role.getRoleName());
            });

        }


        try{
            AppUser myAppUser = userService.getUserById(1L);

            System.out.println("initial users exist skipping...");
        }catch(Exception e){

            System.out.println("initial users not found ..starting insert...");


            AppUser landi = new AppUser(0L, "Landi", encoder.encode("Landi"), "landi@email.com", Set.of(roleService.getByRoleName("ROLE_USER")),Collections.emptySet());
            AppUser donald = new AppUser(0L, "Donald", encoder.encode("Donald"), "donald@gmail.com", Set.of(roleService.getByRoleName("ROLE_ADMIN")),Collections.emptySet());


            List.of(landi,donald ).forEach(user -> {

                userService.saveUser(user);
                System.out.println("inserted user " + user.getUsername());

            });




        }



        try{

            Category category = categoryService.findById(1L);

            System.out.println("initial categories exist skipping!");

        }catch(Exception e){

            System.out.println("initial categories not found starting insert!");

            Category category = new Category(0L, "Electronics","",Collections.emptyList());


            Category category1 = new Category(0L, "Beverage","",Collections.emptyList());
            Category category2 = new Category(0L,"AUTO","",Collections.emptyList());
            List.of(category,category1,category2).forEach(categor -> {
                categoryService.saveCategory(categor);

                System.out.println("inserted category with name " + categor.getName());
            });
        }

        try {

            AuctionItem auctionItem = auctionItemService.getById(1L);

            System.out.println("initial auction items exist skipping!");

        }catch (Exception e){

            System.out.println("initial auction items not found starting insert!");

            AuctionItem mobilePhone = new AuctionItem(0L, "Siemens", null);

            AuctionItem oldWine = new AuctionItem(0L, "Chateau Latour 1959", null);

            List.of(mobilePhone, oldWine).forEach(item -> {

                auctionItemService.saveAuctionItem(item);
                System.out.println("inserted auction item with name " + item.getName());
            });
        }

        try{

          Auction auction = auctionService.getAuctionById(1L);

        }catch(Exception e){
            System.out.println("initial auctions not found starting insert!");

            Auction siemens = new Auction(0L, "old mobile phone ", categoryService.findByName("Electronics"), 100.00, LocalDate.now(),LocalDate.now().plusDays(3), null, userService.getUserById(1L).getUsername(), Collections.emptySet(),Collections.emptySet(),Collections.emptySet());
            Auction oldWine = new Auction(0L, "old wine", categoryService.findByName("Beverage"), 200.00, LocalDate.now(),LocalDate.now().plusDays(3),null, userService.getUserById(2L).getUsername(),Collections.emptySet(),Collections.emptySet(),Collections.emptySet());


            List.of(siemens, oldWine).forEach(auc -> {
                auctionService.saveAuction(auc);

                System.out.println("inserted auction with description " + auc.getAuctionDescription());

            });

        }

        try{

            AuctionItem s = auctionItemService.getByName("Siemens");

            AuctionItem w = auctionItemService.getByName("Chateau Latour 1959");

            s.setAuction(auctionService.getAuctionById(1L));
            w.setAuction(auctionService.getAuctionById(2L));

            auctionItemService.saveAuctionItem(s);
            auctionItemService.saveAuctionItem(w);

        }catch (Exception e){


        }




    }


}
