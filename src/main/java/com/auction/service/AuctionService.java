package com.auction.service;

import com.auction.dao.AuctionDaoImpl;
import com.auction.dao.IAuctionDao;
import com.auction.entity.Auction;
import com.auction.entity.Bid;
import com.auction.entity.User;
import com.auction.exception.WorkflowException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuctionService {

    private UserService userService;
    private BidService bidService;
    private IAuctionDao auctionDao = new AuctionDaoImpl();

    public AuctionService(UserService userService, BidService bidService) {
        this.userService = userService;
        this.bidService = bidService;
    }

    public Auction createAuction(Auction auction) {
        try {
            User sellerInfo = userService.getSellerInfo(auction.getSeller());
            if (sellerInfo == null) {
                throw new WorkflowException(404, "Seller not registered!!");
            }
            return auctionDao.createAuction(auction);
        } catch (WorkflowException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
    }

    public Bid closeAuction(String aucId) {
        try {
            Auction auction = auctionDao.getAuction(aucId);
            List<Bid> bidders = bidService.getBuyerInfo(aucId);
//        bidders = filterBiddersOnLimit(bidders, auction);

            Bid bid = closeOnHUB(bidders);
            auction.setActive(false);
            boolean status = auctionDao.update(auction);
            return bid;
        } catch (WorkflowException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    private Bid closeOnHUB(List<Bid> bidders) {
        Collections.sort(bidders, (o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
        Bid bid = new Bid();
        if (bidders.size() == 1) {
            bid = bidders.get(0);
            return bid;
        }
        boolean flag = false;
        for (int i = 0; i < bidders.size(); i++) {
            if (bidders.get(i).getAmount().equals(bidders.get(i + 1).getAmount())) {
                flag = true;
                continue;
            } else {
                if (flag)
                    bid = bidders.get(i + 1);
                else {
                    bid = bidders.get(i);
                }
                flag = false;
                break;
            }
        }
        return bid;
    }

    private List<Bid> filterBiddersOnLimit(List<Bid> bidders, Auction auction) {
        List<Bid> filterList = new ArrayList<>();
        for (Bid bid : bidders) {
            if (bid.getAmount() > auction.getLowestBidLimit() || bid.getAmount() < auction.getHighestBidlimit())
                filterList.add(bid);
        }
        return filterList;
    }

    public double profitLoss(String seller, String auctionId) {
        Auction auction = auctionDao.getAuction(auctionId);
        if (!auction.getSeller().equalsIgnoreCase(seller))
            throw new WorkflowException(400, "Invalid Seller or AuctionId!!");
        List<Bid> bidders = bidService.getBuyerInfo(auctionId);
        Bid bid = closeAuction(auctionId);
        double val = bid != null ? bid.getAmount() : 0d;
        val += bidders.size() * 0.2 * auction.getPartiticipationCost();
        val -= (auction.getHighestBidlimit() + auction.getLowestBidLimit()) / 2;
        return val;
    }

    public Auction getAuctionInfo(String auction) {
        try {
            return auctionDao.getAuction(auction);
        } catch (WorkflowException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
    }
}
