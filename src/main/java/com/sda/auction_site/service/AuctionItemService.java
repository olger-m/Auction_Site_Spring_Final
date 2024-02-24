package com.sda.auction_site.service;


import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.AuctionItem;
import com.sda.auction_site.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class AuctionItemService {

    private final AuctionItemRepository auctionItemRepository;

    public AuctionItem saveAuctionItem(AuctionItem auctionItem){

        return auctionItemRepository.save(auctionItem);
    }

    public AuctionItem getByName(String name){

        return auctionItemRepository.findByName(name);
    }

    public AuctionItem getById(Long id){

        return auctionItemRepository.findById(id).orElseThrow(() -> new RuntimeException("auction item with id " + id + " not found"));
    }
    public AuctionItem getByAuctionId(Long id){

        return auctionItemRepository.findByAuction_Id(id);
    }

    public List<AuctionItem> getByAuctionsIds(List<Long> ids){

        return auctionItemRepository.findByAuction_IdIn(ids);
    }
}
