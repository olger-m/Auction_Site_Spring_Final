package com.sda.auction_site.dto;

import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDto {

    private List<Auction> participatingAuctions;

    private List<Auction> createdAuctions;

    private List<Bid> placedBids;

}
