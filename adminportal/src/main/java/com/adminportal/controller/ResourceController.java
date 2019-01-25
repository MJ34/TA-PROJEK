package com.adminportal.controller;

import com.adminportal.service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ResourceController {

    @Autowired
    private ProdukService produkService;

    @RequestMapping(value = "/produk/removeList", method = RequestMethod.POST)
    public String removeList(
            @RequestBody ArrayList<String> produkIdList, Model model
    ){
        for (String id : produkIdList) {
            String produkId = id.substring(8);
            produkService.removeOne(Long.parseLong(produkId));
        }

        return "delete success";
    }
}
