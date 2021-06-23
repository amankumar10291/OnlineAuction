package com.auction.service;

import com.auction.dao.IUserDao;
import com.auction.dao.UserDaoImpl;
import com.auction.entity.User;
import com.auction.exception.WorkflowException;
import lombok.Data;

@Data
public class UserService {

    IUserDao userDao = new UserDaoImpl();

    public User addBuyer(String name) {
        if (name == null)
            throw new WorkflowException(400, "Name is empty!!!");
        try {
            User user = new User(name);
            return userDao.addBuyer(user);
        } catch (WorkflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User addSeller(String name) {
        if (name == null)
            throw new WorkflowException(400, "Name is empty!!!");
        try {
            User user = new User(name);
            return userDao.addSeller(user);
        } catch (WorkflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public User getSellerInfo(String seller) {
        return userDao.getSellerInfo(seller);
    }

    public boolean isValidBuyer(String name) {
        return userDao.isBuyerRegistered(name);
    }
}
