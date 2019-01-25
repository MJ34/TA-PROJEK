package com.adminportal.controller;

import com.adminportal.domain.*;
import com.adminportal.service.CartItemService;
import com.adminportal.service.OrderService;
import com.adminportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/produk/produkList";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/orderList")
    public String orderList(Model model) {
        List<Order> orderList = orderService.findAll();

        model.addAttribute("orderList", orderList);

        return "transaksiOrder";
    }

}
