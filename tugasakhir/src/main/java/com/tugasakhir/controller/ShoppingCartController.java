package com.tugasakhir.controller;

import com.tugasakhir.domain.Produk;
import com.tugasakhir.domain.CartItem;
import com.tugasakhir.domain.ShoppingCart;
import com.tugasakhir.domain.User;
import com.tugasakhir.service.ProdukService;
import com.tugasakhir.service.CartItemService;
import com.tugasakhir.service.ShoppingCartService;
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
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(
            @ModelAttribute("produk") Produk produk,
            @ModelAttribute("qty") String qty,
            Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        produk = produkService.findOne(produk.getId());

        if(Integer.parseInt(qty)> produk.getStok()) {
            model.addAttribute("notEnoughStok", true);
            return "forward:/produkDetail?id="+ produk.getId();
        }

        CartItem cartItem = cartItemService.addProdukToCartItem(produk, user, Integer.parseInt(qty));
        model.addAttribute("addProdukSuccess", true);

        return "forward:/produkDetail?id="+ produk.getId();
    }

    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id) {
        cartItemService.removeCartItem(cartItemService.findById(id));

        return "forward:/shoppingCart/cart";
    }

    @RequestMapping("/updateCartItem")
    public String updateShoppingCart(
            @ModelAttribute("id") Long cartItemId,
            @ModelAttribute("qty") int qty
            ) {
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQty(qty);

        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart/cart";
    }
}
