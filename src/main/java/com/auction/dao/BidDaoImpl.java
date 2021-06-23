package com.auction.dao;

import com.auction.entity.Bid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BidDaoImpl implements IBidDao {

    private HashMap<String, List<Bid>> bidMap = new HashMap<>();
    private HashMap<String, List<String>> auctionBidMap = new HashMap<>();

    @Override
    public Bid createBid(Bid bid) {
        List<Bid> bids;
        if (!bidMap.containsKey(bid.getName())) {
            bids = new ArrayList<>();
            bids.add(bid);
            bidMap.put(bid.getName(), bids);
        } else {
            bids = bidMap.get(bid.getName());
            bids.add(bid);
            bidMap.replace(bid.getName(), bids);
        }
        List<String> buyers;
        if (auctionBidMap.containsKey(bid.getAuction())) {
            buyers = auctionBidMap.get(bid.getAuction());
            buyers.add(bid.getName());
            auctionBidMap.replace(bid.getAuction(), buyers);
        } else {
            buyers = new ArrayList<>();
            buyers.add(bid.getName());
            auctionBidMap.put(bid.getAuction(), buyers);
        }
        return bid;
    }

    @Override
    public Bid updateBid(Bid bid) {
        List<Bid> bids = bidMap.get(bid.getName());
        for (Bid bid1 : bids) {
            if (bid1.getAuction().equalsIgnoreCase(bid.getAuction())) {
                bid1.setAmount(bid.getAmount());
                break;
            }
        }
        bidMap.replace(bid.getName(), bids);
        return bid;
    }

    @Override
    public boolean hasBid(Bid bid) {
        if (auctionBidMap.containsKey(bid.getAuction())) {
            return auctionBidMap.get(bid.getAuction()).contains(bid.getName());
        }
        return false;
    }

    @Override
    public Boolean removeBid(Bid bid) {
        if (bidMap.containsKey(bid.getName())) {
            List<Bid> bids = bidMap.get(bid.getName());
            int i;
            for (i = 0; i < bids.size(); i++) {
                if (bids.get(i).getAuction().equalsIgnoreCase(bid.getAuction())) {
                    break;
                }
            }
            bids.remove(i);
            if (bids.size() == 0) {
                bidMap.remove(bid.getName());
            } else {
                bidMap.replace(bid.getName(), bids);
            }
            auctionBidMap.get(bid.getAuction()).remove(bid.getName());
            return true;
        }
        return false;
    }

    @Override
    public List<Bid> getBuyerInfo(String aucId) {
        List<Bid> bidders = new ArrayList<>();
        List<String> buyers = auctionBidMap.get(aucId);
        for(String buyer: buyers){
            List<Bid> list = bidMap.get(buyer);
            for(Bid bid: list){
                if(bid.getAuction().equalsIgnoreCase(aucId)){
                    bidders.add(bid);
                }
            }
        }
        return bidders;
    }
}
