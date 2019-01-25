package com.tugasakhir.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;


@Entity
public class Produk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String tglMasuk;
    private String grade;
    private String kategori;
    private double panjang;
    private double lebar;
    private double tebal;
    private double stok;
    private double harga;
    private boolean active = true;

    @Column(columnDefinition = "text")
    private String deskripsi;

    @Transient
    private MultipartFile produkImage;

    @OneToMany(mappedBy = "produk")
    @JsonIgnore
    private List<ProdukToCartItem> produkToCartItemList;

    public Produk() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getPanjang() {
        return panjang;
    }

    public void setPanjang(double panjang) {
        this.panjang = panjang;
    }

    public double getLebar() {
        return lebar;
    }

    public void setLebar(double lebar) {
        this.lebar = lebar;
    }

    public double getTebal() {
        return tebal;
    }

    public void setTebal(double tebal) {
        this.tebal = tebal;
    }

    public double getStok() {
        return stok;
    }

    public void setStok(double stok) {
        this.stok = stok;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public MultipartFile getProdukImage() {
        return produkImage;
    }

    public void setProdukImage(MultipartFile produkImage) {
        this.produkImage = produkImage;
    }

    public List<ProdukToCartItem> getProdukToCartItemList() {
        return produkToCartItemList;
    }

    public void setProdukToCartItemList(List<ProdukToCartItem> produkToCartItemList) {
        this.produkToCartItemList = produkToCartItemList;
    }

}