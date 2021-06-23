package com.auction.dao;

import com.auction.entity.Auction;
import com.auction.exception.WorkflowException;

import java.util.HashMap;

public class AuctionDaoImpl implements IAuctionDao {

    private static Integer auctionId = 1;
    private HashMap<String, Auction> auctionMap = new HashMap<>();

    @Override
    public Auction createAuction(Auction auction) {
        if (auction == null)
            throw new WorkflowException(400, "Auction data is empty!!");
        if (auctionMap.containsKey(auction.getId())) {
            throw new WorkflowException(400, "Auction with this Id already registered!!");
        }
        auctionMap.put(auction.getId(), auction);
        return auction;
    }

    @Override
    public Auction getAuction(String auctionId) {
        if (auctionId == null)
            throw new WorkflowException(400, "Auction Id is empty!!");
        if (auctionMap.containsKey(auctionId)) {
            return auctionMap.get(auctionId);
        }
        throw new WorkflowException(400, "Auction with this Id not registered!!");
    }

    @Override
    public boolean update(Auction auction) {
        if(auctionMap.containsKey(auction.getId())){
            auctionMap.replace(auction.getId(),auction);
            return true;
        }
        return false;
    }
}
