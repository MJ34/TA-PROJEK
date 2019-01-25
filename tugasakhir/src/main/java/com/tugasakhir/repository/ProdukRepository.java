package com.tugasakhir.repository;

import com.tugasakhir.domain.Produk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdukRepository extends CrudRepository<Produk, Long> {

    List<Produk> findByKategori(String kategori);

    List<Produk> findByTitleContaining(String title);
}
