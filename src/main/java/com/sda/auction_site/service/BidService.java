package com.sda.auction_site.service;



import com.sda.auction_site.model.Bid;

import com.sda.auction_site.repository.AuctionRepository;
import com.sda.auction_site.repository.BidRepository;
import com.sda.auction_site.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BidService {

    private final BidRepository bidRepository;



    public Bid saveBid(Bid bid){

        return bidRepository.save(bid);
    }
    public List<Bid> findByUsername(String username){

        return bidRepository.findByUsername(username);
    }
    public List<Bid> getAllBids(){
        return bidRepository.findAll();
    }

    public List<Bid> getByAuctionId(Long id){

        return bidRepository.findByAuction_Id(id);
    }

    public Bid getBidById(Long bidId) {
        return  bidRepository.findById(bidId).orElseThrow(()->new RuntimeException("bid not found for id "+bidId));
    }
}
