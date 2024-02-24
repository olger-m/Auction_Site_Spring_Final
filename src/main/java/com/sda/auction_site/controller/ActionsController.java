package com.sda.auction_site.controller;


import com.sda.auction_site.model.AppUser;
import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.AuctionItem;
import com.sda.auction_site.model.Bid;
import com.sda.auction_site.service.AuctionItemService;
import com.sda.auction_site.service.AuctionService;
import com.sda.auction_site.service.BidService;
import com.sda.auction_site.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/biddingapp/v1/")
@AllArgsConstructor
public class ActionsController {


    private final UserService userService;


    private final AuctionService auctionService;


    private final BidService bidService;
    private final AuctionItemService auctionItemService;

    @PostMapping("bid/add")
    public ResponseEntity<?> addBid(Principal principal, @RequestParam(value = "auctionId") Long auctionId, @RequestParam(value = "bidAmount") Double bidAmount) {

        Auction auction = auctionService.getAuctionById(auctionId);
        AppUser appUser = (AppUser) userService.loadUserByUsername(principal.getName());
        appUser.addAuction(auction);
        auction.addParticipant(appUser);

        AppUser mUser = userService.updateUser(appUser);

        Bid bid = new Bid();
        bid.setBidAmount(bidAmount);
        bid.setUsername(mUser.getUsername());

        auction.addBid(bid);

        Auction mAuction = auctionService.saveAuction(auction);

        bid.setAuction(mAuction);
//        auction.addBid(bid);
//
        bidService.saveBid(bid);


//


        Map<String, Boolean> info = new HashMap<>();

        info.put("inserted", true);

        return ResponseEntity.ok().body(info);

    }


    @PostMapping("/additem")
    public ResponseEntity<?> addItemAuction(@RequestParam(value = "auctionId") Long auctionId, @RequestBody AuctionItem auctionItemFront){
        Auction auction = auctionService.getAuctionById(auctionId);

        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setName(auctionItemFront.getName());

        auction.addAuctionItem(auctionItem);

        Auction mAuction = auctionService.saveAuction(auction);

        auctionItem.setAuction(mAuction);
//        auction.addBid(bid);
//
        auctionItemService.saveAuctionItem(auctionItem);


//


        Map<String, Boolean> info = new HashMap<>();

        info.put("inserted", true);

        return ResponseEntity.ok().body(info);

    }


    @PostMapping("updateBid")
    public ResponseEntity<?> UpdateBid(Principal principal, @RequestParam(value = "bidId") Long bidId, @RequestParam(value = "bidAmount") Double bidAmount) {

        Bid bid = bidService.getBidById(bidId);
        if (bid.getUsername().equals(principal.getName())){
            bid.setBidAmount(bidAmount);;
            bidService.saveBid(bid);
        }


//


        Map<String, Boolean> info = new HashMap<>();

        info.put("inserted", true);

        return ResponseEntity.ok().body(info);

    }

    @PostMapping("leave")
    public ResponseEntity<?> LeaveAuction(Principal principal, @RequestParam(value = "auctionId") Long auctionId,@RequestBody Auction auction) {

        String user=principal.getName();
        auction=auctionService.getAuctionById(auctionId);
        Auction finalAuction = auction;
        auction.getBids().stream().forEach(bid ->{
            if (bid.getUsername().equals(user)) {
                finalAuction.getBids().remove(bid);
                System.out.println(bid.getId());
            }});

//


        Map<String, Boolean> info = new HashMap<>();

        info.put("inserted", true);

        return ResponseEntity.ok().body(info);

    }

//    @PostMapping("/winner")
//    public ResponseEntity<?> getWinner(@RequestParam(value = "auctionId") Long auctionId, @RequestBody Auction auction){
//        auction = auctionService.getAuctionById(auctionId);
//        AppUser appUser = (AppUser) userService.loadUserByUsername(auctionService.getWinner(auction));
//        auction.setWinnerUsername(appUser.getUsername());
//        auctionService.saveAuction(auction);
//        Map<String, Boolean> info = new HashMap<>();
//
//        info.put("inserted", true);
//        return ResponseEntity.ok().body(info);
//    }


}
