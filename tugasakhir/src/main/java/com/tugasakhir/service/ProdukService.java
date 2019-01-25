package com.tugasakhir.service;

import com.tugasakhir.domain.Produk;

import java.util.List;

public interface ProdukService {
    List<Produk> findAll();

    Produk findOne(Long id);

    Produk save(Produk produk);

    List<Produk> findByKategori(String kategori);

    List<Produk> blurrySearch(String title);

}
