package com.adminportal.repository;

import com.adminportal.domain.Order;
import com.adminportal.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUser(User user);

}
