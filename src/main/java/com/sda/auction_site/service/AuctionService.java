package com.sda.auction_site.service;



import com.sda.auction_site.model.*;

import com.sda.auction_site.repository.AuctionRepository;
import com.sda.auction_site.repository.BidRepository;
import com.sda.auction_site.repository.CategoryRepository;
import com.sda.auction_site.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalDate;

import java.util.Comparator;
import java.util.List;


@Service
@AllArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private  final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;


    public Auction getAuctionById(Long id){

        return auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("auction with id " +id +" not found"));
    }

    public List<Auction> getAuctionByUserId(Long id){

        return auctionRepository.findByParticipants_Id(id);
    }


    public Auction saveAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public void deleteAuctionById(Long auctionid) {
        auctionRepository.deleteById(auctionid);
    }

    public Auction updateAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

//
    public List<Auction> findAuctionByMinimumAmountLessThan(Double amount){
        return auctionRepository.findAuctionByMinimumAmount(amount);
    }


    public List<Auction> listActiveAuctions() {
        LocalDate now = LocalDate.now();
        return auctionRepository.findAllByEndDateAfter(now);
    }

    public List<Auction> getAll(){

        return auctionRepository.findAll();
    }

    public List<Auction> getByCreatorUsername(String username){

        return auctionRepository.findByCreatorUsername(username);
    }

    public AppUser getWinner(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        Bid winningBid = auction.getBids().stream().max(Comparator.comparing(Bid::getBidAmount)).orElseThrow(()->new RuntimeException("NO bids doidund"));
        auction.setEndDate(LocalDate.now());
        AppUser bidWiner = userRepository.findByUsername(winningBid.getUsername());
        return bidWiner;
    }

    public void deleteBid(Long id) {
        bidRepository.deleteById(id);
    }

//    public void closeAuction(Long auctionId) {
//        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
//            auction.setEndDate(LocalDate.now());
//            auctionRepository.save(auction);
//    }

}
