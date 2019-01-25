package com.tugasakhir.repository;

import com.tugasakhir.domain.ProdukToCartItem;
import com.tugasakhir.domain.CartItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProdukToCartItemRepository extends CrudRepository<ProdukToCartItem,Long>{

    void deleteByCartItem (CartItem cartItem);
}
