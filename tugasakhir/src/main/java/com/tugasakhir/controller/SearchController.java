package com.tugasakhir.controller;

import com.tugasakhir.domain.Produk;
import com.tugasakhir.domain.User;
import com.tugasakhir.service.ProdukService;
import com.tugasakhir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProdukService produkService;

    @Autowired
    private UserService userService;

    @RequestMapping("/searchByKategori")
    public String searchByKategori(
            @RequestParam("kategori") String kategori,
            Model model, Principal principal
    ) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        String classActiveKategori = "active"+kategori;
        classActiveKategori=classActiveKategori.replaceAll("\\s+","");
        classActiveKategori=classActiveKategori.replaceAll("&","");
        model.addAttribute(classActiveKategori, true);

        List<Produk> produkList = produkService.findByKategori(kategori);

        if (produkList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "produkshelf";
        }

        model.addAttribute("produkList", produkList);

        return "produkshelf";
    }

    @RequestMapping("/searchProduk")
    public String searchProduk(
            @ModelAttribute("keyword") String keyword,
            Principal principal, Model model
            ) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<Produk> produkList = produkService.blurrySearch(keyword);

        if (produkList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "produkshelf";
        }

        model.addAttribute("produkList", produkList);

        return "produkshelf";
    }
}
