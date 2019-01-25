package com.adminportal.service.impl;

import com.adminportal.domain.Order;
import com.adminportal.domain.Produk;
import com.adminportal.repository.OrderRepository;
import com.adminportal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }


}
