package com.adminportal.service.impl;

import com.adminportal.domain.CartItem;
import com.adminportal.domain.Order;
import com.adminportal.repository.CartItemRepository;
import com.adminportal.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findByOrder(Order order) {
        return cartItemRepository.findByOrder(order);
    }

    @Override
    public List<CartItem> findAll() {
        return (List<CartItem>) cartItemRepository.findAll();
    }
}
