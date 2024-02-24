package com.sda.auction_site.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auctionDescription;

    @ManyToOne
    private Category category;

    private Double minimumAmount;

    private LocalDate dateOfIssue;

    private LocalDate endDate;

    @Nullable
    private String winnerUsername;

    private String creatorUsername;

    @ManyToMany(mappedBy = "registeredAuctions")
    @JsonIgnore
    private Set<AppUser> participants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "auction", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<AuctionItem> auctionItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "auction", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    public void addParticipant(AppUser appUser){

        if(!participants.contains(appUser)){
            this.participants.add(appUser);
        }

    }

    public void addBid(Bid bid){

        this.bids.add(bid);
    }


    public void addAuctionItem(AuctionItem auctionItem){

        this.auctionItems.add(auctionItem);
    }

}

