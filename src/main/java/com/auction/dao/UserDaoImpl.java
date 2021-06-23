package com.auction.dao;

import com.auction.entity.User;
import com.auction.exception.WorkflowException;
import lombok.Data;

import java.util.HashMap;

@Data
public class UserDaoImpl implements IUserDao {

    private HashMap<String, User> buyerMap = new HashMap<>();
    private HashMap<String, User> sellerMap = new HashMap<>();

    public User addBuyer(User user) {
        if (user == null) {
            throw new WorkflowException(400, "Provide valid data for buyer!!");
        }
        if (buyerMap.containsKey(user.getUsername())) {
            throw new WorkflowException(400, "Buyer Already added!!");
        } else {
            buyerMap.put(user.getUsername(), user);
            return user;
        }
    }

    public User addSeller(User user) {
        if (user == null) {
            throw new WorkflowException(400, "Provide valid data for seller!!");
        }
        if (sellerMap.containsKey(user.getUsername())) {
            throw new WorkflowException(400, "Seller Already added!!");
        } else {
            sellerMap.put(user.getUsername(), user);
            return user;
        }
    }

    @Override
    public User getSellerInfo(String seller) {
        if (sellerMap.containsKey(seller))
            return sellerMap.get(seller);
        return null;
    }

    @Override
    public User getBuyerInfo(String buyer) {
        if (buyerMap.containsKey(buyer))
            return buyerMap.get(buyer);
        return null;
    }

    @Override
    public boolean isBuyerRegistered(String name) {
        return buyerMap.containsKey(name);
    }

}
