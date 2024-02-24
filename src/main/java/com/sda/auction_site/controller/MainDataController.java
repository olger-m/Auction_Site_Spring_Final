package com.sda.auction_site.controller;


import com.sda.auction_site.model.AppUser;
import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.Bid;
import com.sda.auction_site.repository.BidRepository;
import com.sda.auction_site.service.AuctionItemService;
import com.sda.auction_site.service.AuctionService;
import com.sda.auction_site.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/biddingapp/v1/data/")
@AllArgsConstructor
public class MainDataController {


    private final CategoryService categoryService;

    private final AuctionService auctionService;

    private final AuctionItemService auctionItemService;
    private final BidRepository bidRepository;

    @GetMapping("category/all")
    public ResponseEntity<?> getAllCategories() {

        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }


    @GetMapping("auction/all")
    public ResponseEntity<?> getAllAuctions() {

        return ResponseEntity.ok().body(auctionService.listActiveAuctions());
    }
    @GetMapping("auction/winner")
    public AppUser getAuctionWinner(@RequestParam Long id){
        Auction auction= auctionService.getAuctionById(id);
        AppUser winner = auctionService.getWinner(id);
        auction.setWinnerUsername(winner.getUsername());
        auctionService.saveAuction(auction);
        return winner;

    }
    @GetMapping("auction/leave")
    public AppUser leaveauction(Principal principal, @RequestParam Long id){
        List<Long> toRemove = new ArrayList();
        Auction auction= auctionService.getAuctionById(id);
        String user=principal.getName();
        auction.getBids().stream().forEach(bid ->{
            if (bid.getUsername().equals(user)) {
                toRemove.add(bid.getId());
            }});
        for (Long bidid:toRemove
             ) {
            Bid bid = bidRepository.findById(bidid).get();
            bid.setAuction(null);
            bidRepository.save(bid);

        }


//        auctionService.saveAuction(finalAuction);
        return new AppUser();

    }


    @GetMapping("items/all")
    public ResponseEntity<?> getAllActiveItems(){

        List<Long> activeAuctionsIds = auctionService.listActiveAuctions().stream().map(Auction::getId).toList();

        return ResponseEntity.ok().body(auctionItemService.getByAuctionsIds(activeAuctionsIds));
    }



}
