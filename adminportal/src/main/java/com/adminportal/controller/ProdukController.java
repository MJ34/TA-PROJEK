package com.adminportal.controller;

import com.adminportal.domain.Produk;
import com.adminportal.service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/produk")
public class ProdukController {

    private Path path;

    @Autowired
    private ProdukService produkService;

    @RequestMapping("/produkList")
    public String produkList(Model model) {
        List<Produk> produkList = produkService.findAll();

        model.addAttribute("produkList", produkList);

        return "produkList";
    }

    @RequestMapping("/add")
    public String addProduk(Model model) {
        Produk produk = new Produk();
        model.addAttribute("produk", produk);
        return "addproduk";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProdukPost(@ModelAttribute("produk") Produk produk, HttpServletRequest request) {
        produkService.save(produk);

        MultipartFile produkImage = produk.getProdukImage();

        try {
            byte[] bytes = produkImage.getBytes();
            String name = produk.getId() + ".png";
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/produk/"+name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:produkList";
    }

    @RequestMapping("/produkInfo")
    public String produkInfo(@RequestParam("id") Long id, Model model) {
        Produk produk = produkService.findOne(id);
        model.addAttribute("produk", produk);

        return "produkInfo";
    }

    @RequestMapping("/updateProduk")
    public String updateProduk(@RequestParam("id") Long id, Model model){
        Produk produk = produkService.findOne(id);
        model.addAttribute("produk", produk);

        return "updateProduk";
    }

    @RequestMapping(value = "/updateProduk", method = RequestMethod.POST)
    public String updateProdukPost(@ModelAttribute("produk") Produk produk, HttpServletRequest request) {
        produkService.save(produk);

        MultipartFile produkImage = produk.getProdukImage();

        if(!produkImage.isEmpty()) {
            try {

                byte[] bytes = produkImage.getBytes();
                String name = produk.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/produk/"+name));

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/produk/"+name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/produk/produkInfo?id="+ produk.getId();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ){
        produkService.removeOne(Long.parseLong(id.substring(8)));

        List<Produk> produkList = produkService.findAll();

        model.addAttribute("produkList", produkList);

        return "redirect:/produk/produkList";
    }


}
