package com.auction.service;

import com.auction.dao.BidDaoImpl;
import com.auction.dao.IBidDao;
import com.auction.entity.Bid;
import com.auction.exception.WorkflowException;

import java.util.List;

public class BidService {

    private IBidDao bidDao = new BidDaoImpl();

    public Bid updateBid(Bid bid) {
        try {
            if (bidDao.hasBid(bid))
                return bidDao.updateBid(bid);
            return bidDao.createBid(bid);
        } catch (WorkflowException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
    }

    public Boolean withdrawBid(Bid bid) {
        if (bidDao.hasBid(bid)){
            return bidDao.removeBid(bid);
        }
        throw new WorkflowException(400,"Buyer didn't bid!!");
    }

    public List<Bid> getBuyerInfo(String aucId) {
        return bidDao.getBuyerInfo(aucId);
    }

}
