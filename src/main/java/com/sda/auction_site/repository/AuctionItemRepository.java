package com.sda.auction_site.repository;


import com.sda.auction_site.model.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem,Long> {


    AuctionItem findByName(String name);

    AuctionItem findByAuction_Id(Long id);

    List<AuctionItem> findByAuction_IdIn(List<Long> ids);
}
