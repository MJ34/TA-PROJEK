package com.adminportal.service.impl;

import com.adminportal.domain.Produk;
import com.adminportal.repository.ProdukRepository;
import com.adminportal.service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdukServiceImpl implements ProdukService {

    @Autowired
    private ProdukRepository produkRepository;

    @Override
    public Produk save(Produk produk) {
        return produkRepository.save(produk);
    }

    @Override
    public List<Produk> findAll() {
        return (List<Produk>) produkRepository.findAll();
    }

    @Override
    public Produk findOne(Long id) {
        return produkRepository.findOne(id);
    }

    public void removeOne(Long id) {
        produkRepository.delete(id);
    }
}
