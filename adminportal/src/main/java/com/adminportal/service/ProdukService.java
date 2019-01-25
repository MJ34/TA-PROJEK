package com.adminportal.service;

import com.adminportal.domain.Produk;

import java.util.List;


public interface ProdukService {
    Produk save(Produk produk);

    Produk findOne(Long id);

    List<Produk> findAll();

    void removeOne(Long id);
}
