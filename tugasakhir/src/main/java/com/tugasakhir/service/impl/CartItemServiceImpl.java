package com.tugasakhir.service.impl;

import com.tugasakhir.domain.*;
import com.tugasakhir.repository.ProdukToCartItemRepository;
import com.tugasakhir.repository.CartItemRepository;
import com.tugasakhir.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProdukToCartItemRepository produkToCartItemRepository;


    public CartItem addProdukToCartItem(Produk produk, User user, int qty ) {

        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (produk.getId() == cartItem.getProduk().getId()) {
                cartItem.setQty(cartItem.getQty()+qty);
                cartItem.setSubtotal(new BigDecimal(produk.getHarga()).multiply(new BigDecimal(qty)));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setProduk(produk);

        cartItem.setQty(qty);
        cartItem.setSubtotal(new BigDecimal(produk.getHarga()).multiply(new BigDecimal(qty)));

        cartItem = cartItemRepository.save(cartItem);

        ProdukToCartItem produkToCartItem = new ProdukToCartItem();
        produkToCartItem.setProduk(produk);
        produkToCartItem.setCartItem(cartItem);
        produkToCartItemRepository.save(produkToCartItem);

        return cartItem;
    }

    public void removeCartItem (CartItem cartItem) {
        produkToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    public CartItem updateCartItem(CartItem cartItem){
        BigDecimal bigDecimal = new BigDecimal(cartItem.getProduk().getHarga()).multiply(new BigDecimal(cartItem.getQty()));
        bigDecimal=bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        cartItem.setSubtotal(bigDecimal);

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findOne(id);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> findByOrder(Order order) {
        return cartItemRepository.findByOrder(order);
    }

}
