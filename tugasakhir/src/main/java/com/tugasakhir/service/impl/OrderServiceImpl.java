package com.tugasakhir.service.impl;

import com.tugasakhir.domain.*;
import com.tugasakhir.repository.OrderRepository;
import com.tugasakhir.repository.ShippingAddressRepository;
import com.tugasakhir.service.ProdukService;
import com.tugasakhir.service.CartItemService;
import com.tugasakhir.service.OrderService;
import com.tugasakhir.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private MailConstructor mailConstructor;

    public synchronized Order createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            User user
            ) {
        Order order = new Order();
        order.setOrderStatus("created");
        order.setShippingAddress(shippingAddress);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            Produk produk = cartItem.getProduk();
            cartItem.setOrder(order);
            produk.setStok(produk.getStok()-cartItem.getQty());
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);


        return order;
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }


}
