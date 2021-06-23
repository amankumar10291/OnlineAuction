package com.auction.dao;

import com.auction.entity.User;

public interface IUserDao {
    User addBuyer(User user);

    User addSeller(User user);

    User getSellerInfo(String seller);
    User getBuyerInfo(String buyer);

    boolean isBuyerRegistered(String name);
}
