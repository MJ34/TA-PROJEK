package com.tugasakhir.repository;

import com.tugasakhir.domain.CartItem;
import com.tugasakhir.domain.Order;
import com.tugasakhir.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    List<CartItem> findByOrder(Order order);

}
