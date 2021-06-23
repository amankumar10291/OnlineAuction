package com.auction.dao;

import com.auction.entity.Auction;

public interface IAuctionDao {
    Auction createAuction(Auction auction);

    Auction getAuction(String auctionId);

    boolean update(Auction auction);
}
