package com.tugasakhir.repository;

import com.tugasakhir.domain.Order;
import com.tugasakhir.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
}
