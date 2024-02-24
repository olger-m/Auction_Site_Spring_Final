package com.sda.auction_site;

import com.sda.auction_site.model.Auction;
import com.sda.auction_site.repository.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AuctionSiteApplicationTests {

	@Autowired
	private AuctionRepository auctionRepository;
	@Test
	void AuctionByUserId() {

//		List<Auction> auctionList = auctionRepository.findByUsers_id(1L);
	}

}
