package com.tugasakhir.service;

import com.tugasakhir.domain.*;

public interface OrderService {
    Order createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            User user
    );

    Order findOne(Long id);
}
