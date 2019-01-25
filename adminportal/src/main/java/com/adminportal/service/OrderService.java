package com.adminportal.service;

import com.adminportal.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findOne(Long id);

}
