package com.adminportal.repository;

import com.adminportal.domain.CartItem;
import com.adminportal.domain.Order;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    List<CartItem> findByOrder(Order order);
}
