package com.sda.auction_site.controller;


import com.sda.auction_site.dto.UserInfoDto;
import com.sda.auction_site.model.*;
import com.sda.auction_site.service.AuctionService;
import com.sda.auction_site.service.BidService;
import com.sda.auction_site.service.CategoryService;
import com.sda.auction_site.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/biddingapp/v1/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    private final BidService bidService;


    private final AuctionService auctionService;

    private final CategoryService categoryService;


    @GetMapping("profile")
    public ResponseEntity<?> getProfile(Principal principal){

        AppUser appUser = (AppUser) userService.loadUserByUsername(principal.getName());
        List<Bid> bidList = bidService.findByUsername(appUser.getUsername());

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setPlacedBids(bidList);

        List<Auction> participatingAuctions = auctionService.getAuctionByUserId(appUser.getId());

        List<Auction> createdAuctions = auctionService.getByCreatorUsername(principal.getName());
        userInfoDto.setParticipatingAuctions(participatingAuctions);
        userInfoDto.setCreatedAuctions(createdAuctions);

        return ResponseEntity.ok().body(userInfoDto);
    }

    @PostMapping("/register")
    public AppUser createUser(@RequestBody AppUser appUser) {

        return userService.saveNewUser(appUser);

    }
    @PostMapping("/createauction")
    public Auction createAuction(@RequestBody Auction auction,@RequestParam String category){
        Category category1=categoryService.findByName(category);
        category1.setAuctionList(Collections.emptyList());
        auction.setCategory(category1);
        return auctionService.saveAuction(auction);
    }





}
