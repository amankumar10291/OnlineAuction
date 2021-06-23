package com.auction.resource;

import com.auction.entity.Auction;
import com.auction.entity.Bid;
import com.auction.entity.User;
import com.auction.exception.WorkflowException;
import com.auction.service.AuctionService;
import com.auction.service.BidService;
import com.auction.service.UserService;

public class AuctionManager {

    private UserService userService = new UserService();
    private BidService bidService = new BidService();
    private AuctionService auctionService = new AuctionService(userService, bidService);

    public User addBuyer(String buyer) {
        try {
            return userService.addBuyer(buyer.toUpperCase());
        } catch (WorkflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User addSeller(String seller) {
        try {
            return userService.addSeller(seller.toUpperCase());
        } catch (WorkflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Auction createAuction(String aData) {
        String[] data = aData.split(",");
        if (data.length < 5) {
            System.out.println("ERROR: Insufficient data provided");
            return null;
        }
        if (Long.parseLong(data[1]) >= Long.parseLong(data[2])) {
            System.out.println("Higher limit should be greater than Lower limit");
            return null;
        }
        Auction auction = new Auction(data[0], Long.parseLong(data[1]), Long.parseLong(data[2]), Long.parseLong(data[3]), data[4].toUpperCase(), true);
        try {
            return auctionService.createAuction(auction);
        } catch (WorkflowException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Bid createUpdateBid(String bidData) {
        String[] data = bidData.split(",");
        if (data.length < 3) {
            System.out.println("ERROR: Insufficient data provided");
            return null;
        }

        Bid bid = new Bid(data[0], data[1], Long.parseLong(data[2]));
        boolean isValid = userService.isValidBuyer(bid.getName().toUpperCase());
        if (isValid) {
            Auction auction = auctionService.getAuctionInfo(bid.getAuction());
            if (auction != null && bid.getAmount() >= auction.getLowestBidLimit() && bid.getAmount() <= auction.getHighestBidlimit())
                return bidService.updateBid(bid);
            else{
                System.out.println("ERROR: Invalid Bid!!");
                return null;
            }
        }
        System.out.println("ERROR: Invalid Bid, buyer not registered!!");
        return null;

    }

    public Boolean withdrawBid(String withdraw) {
        String[] data = withdraw.split(",");
        if (data.length < 2) {
            System.out.println("ERROR: Insufficient data provided");
            return null;
        }
        Bid bid = new Bid(data[0].toUpperCase(), data[1], 0l);
        try {
            return bidService.withdrawBid(bid);
        } catch (WorkflowException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Bid closeAuction(String aucId) {
        return auctionService.closeAuction(aucId);
    }

    public double getProfitLoss(String pl) {
        String[] data = pl.split(",");
        if (data.length < 2) {
            System.out.println("ERROR: Insufficient data provided");
            return 0d;
        }
        return auctionService.profitLoss(data[0], data[1]);
    }
}
