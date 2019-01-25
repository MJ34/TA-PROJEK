package com.tugasakhir.service;

import com.tugasakhir.domain.*;

import java.util.List;

public interface CartItemService {

    CartItem addProdukToCartItem(Produk produk, User user, int qty );

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    List<CartItem> findByOrder(Order order);

    CartItem updateCartItem(CartItem cartItem);

    void removeCartItem (CartItem cartItem);

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);
}
