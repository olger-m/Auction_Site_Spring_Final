package com.sda.auction_site.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;


    private Double bidAmount;

    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "main_auction_id")
    @JsonIgnore
    private Auction auction;

    public void setAuction(Auction auction){

        this.auction = auction;
    }

}
