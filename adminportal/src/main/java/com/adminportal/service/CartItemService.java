package com.adminportal.service;

import com.adminportal.domain.CartItem;
import com.adminportal.domain.Order;

import java.util.List;

public interface CartItemService {

    List<CartItem> findByOrder(Order order);

    List<CartItem> findAll();
}
