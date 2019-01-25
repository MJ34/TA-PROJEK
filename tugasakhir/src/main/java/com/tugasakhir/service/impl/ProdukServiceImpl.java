package com.tugasakhir.service.impl;

import com.tugasakhir.domain.Produk;
import com.tugasakhir.repository.ProdukRepository;
import com.tugasakhir.service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdukServiceImpl implements ProdukService {

    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> findAll() {
        List<Produk> produkList = (List<Produk>) produkRepository.findAll();

        List<Produk> activeProdukList = new ArrayList<>();

        for (Produk produk : produkList) {
            if(produk.isActive()) {
                activeProdukList.add(produk);
            }
        }

        return activeProdukList;
    }

    public Produk findOne(Long id) {
        return produkRepository.findOne(id);
    }

    public Produk save(Produk produk) {
        return produkRepository.save(produk);
    }

    public List<Produk> findByKategori(String kategori) {
        List<Produk> produkList = produkRepository.findByKategori(kategori);

        List<Produk> activeProdukList = new ArrayList<>();

        for (Produk produk : produkList) {
            if(produk.isActive()) {
                activeProdukList.add(produk);
            }
        }

        return activeProdukList;
    }

    public List<Produk> blurrySearch(String title) {
        List<Produk> produkList = produkRepository.findByTitleContaining(title);

        List<Produk> activeProdukList = new ArrayList<>();

        for (Produk produk : produkList) {
            if(produk.isActive()) {
                activeProdukList.add(produk);
            }
        }

        return activeProdukList;
    }
}
