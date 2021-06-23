package com.auction.dao;

import com.auction.entity.Bid;

import java.util.List;

public interface IBidDao {
    Bid createBid(Bid bid);

    Bid updateBid(Bid bid);

    boolean hasBid(Bid bid);

    Boolean removeBid(Bid bid);

    List<Bid> getBuyerInfo(String aucId);
}
