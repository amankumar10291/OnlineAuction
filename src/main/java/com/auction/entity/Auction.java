package com.auction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auction {

    private String id;
    private long lowestBidLimit;
    private long highestBidlimit;
    private long partiticipationCost;
    private String seller;
    private boolean active;

}
