package com.sda.auction_site.repository;

import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.Bid;
import com.sda.auction_site.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction,Long> {


    List<Auction> findAuctionByMinimumAmount(Double amount);


    List<Auction> findAllByEndDateAfter(LocalDate now);

    List<Auction> findByParticipants_Id(Long id);

    List<Auction> findByCreatorUsername(String creatorUsername);


}
