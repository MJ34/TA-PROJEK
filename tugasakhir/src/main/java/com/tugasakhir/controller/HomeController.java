package com.tugasakhir.controller;

import com.tugasakhir.config.SecurityConfig;
import com.tugasakhir.config.SecurityUtility;
import com.tugasakhir.domain.*;
import com.tugasakhir.domain.security.PasswordResetToken;
import com.tugasakhir.domain.security.Role;
import com.tugasakhir.domain.security.UserRole;
import com.tugasakhir.service.*;
import com.tugasakhir.service.impl.UserSecurityService;
import com.tugasakhir.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Produk> produkList = produkService.findAll();
        model.addAttribute("produkList", produkList);
        model.addAttribute("activeAll", true);
        return "index";
    }

    @RequestMapping("/hours")
    public String hours() {
        return "hours";
    }

    @RequestMapping("/faq")
    public String faq() {
        return "faq";
    }

    @RequestMapping("/produkshelf")
    public String produkshelf(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<Produk> produkList = produkService.findAll();
        model.addAttribute("produkList", produkList);
        model.addAttribute("activeAll", true);

        return "produkshelf";
    }

    @RequestMapping("/produkDetail")
    public String produkDetail(@PathParam("id") Long id, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Produk produk = produkService.findOne(id);

        model.addAttribute("produk", produk);
        List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", "1");
        return "produkDetail";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", "true");
        return "myAccount";
    }


    @RequestMapping("/forgetPassword")
    public String forgetPassword(@ModelAttribute("email") String email,
                                 HttpServletRequest request,
                                 Model model) {
        model.addAttribute("classActiveForgetPassword", "true");

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExists", true);
            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();

        SimpleMailMessage newEmail =
                mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", true);

        return "myAccount";
    }

    @RequestMapping("/myProfile")
    public String myProfile(
            Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);


        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);

      //  List<String> stateList = USConstants.listOfUSStatesCode;
      //  Collections.sort(stateList);
      //  model.addAttribute("stateList", stateList);
        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

    @RequestMapping("/orderDetail")
    public String orderDetail(
            @RequestParam("id") Long orderId,
            Principal principal, Model model
    ){
        User user = userService.findByUsername(principal.getName());
        Order order = orderService.findOne(orderId);

        if(order.getUser().getId()!=user.getId()) {
            return "badRequestPage";
        } else {
            List<CartItem> cartItemList = cartItemService.findByOrder(order);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("user", user);
            model.addAttribute("order", order);

            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());

            UserShipping userShipping = new UserShipping();
            model.addAttribute("userShipping", userShipping);

            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("classActiveOrders", true);
            model.addAttribute("displayOrderDetail", true);

            return "myProfile";
        }
    }

   /* @RequestMapping(path = "report", method = RequestMethod.GET)
    public String report(@RequestParam("id") Long orderId,
                         Principal principal) {

       Map<String, Object> model = new HashMap<>();

      //  List<Car> cars = carService.findAll();
       // model.put("cars", cars);

        User user = userService.findByUsername(principal.getName());
        Order order = orderService.findOne(orderId);

        if(order.getUser().getId()!=user.getId()) {
            return "badRequestPage";
        } else {
            List<CartItem> cartItemList = cartItemService.findByOrder(order);
            model.put("cartItemList", cartItemList);
            model.put("user", user);
            model.put("order", order);

            model.put("userShippingList", user.getUserShippingList());
            model.put("orderList", user.getOrderList());

            UserShipping userShipping = new UserShipping();
            model.put("userShipping", userShipping);

            model.put("listOfShippingAddresses", true);

            model.put("classActiveOrders", true);
            model.put("displayOrderDetail", true);

            return String.valueOf(new ModelAndView(new GeneratePdfReport(), model));

        }
    }
*/

    @RequestMapping("/listOfCreditCards")
    public String listOfCreditCards(
            Model model, Principal principal, HttpServletRequest request
    ) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());


        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(
            Model model, Principal principal, HttpServletRequest request
    ) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);

        model.addAttribute("listOfCreditCards", true);
        return "myProfile";
    }

    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(
            Model model, Principal principal
    ) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);


        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);

        UserShipping userShipping = new UserShipping();

        model.addAttribute("userShipping", userShipping);

       // List<String> stateList = USConstants.listOfUSStatesCode;
        //Collections.sort(stateList);
        //model.addAttribute("stateList", stateList);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());


        return "myProfile";
    }

    @RequestMapping(value = "/addNewShippingAddress", method = RequestMethod.POST)
    public String addNewShippingAddressPost(
            @ModelAttribute("userShipping") UserShipping userShipping,
            Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        userService.updateUserShipping(userShipping, user);

        model.addAttribute("user", user);
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        return "myProfile";

    }

    @RequestMapping(value = "/setDefaultShippingAddress", method = RequestMethod.POST)
    public String setDefaultShipping(
            @ModelAttribute("defaultShippingAddressId") Long defaultShippingId, Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultShipping(defaultShippingId, user);

        model.addAttribute("user", user);
        model.addAttribute("userShippingList", user.getUserShippingList());
    //    model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("orderList", user.getOrderList());

        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        return "myProfile";
    }

    @RequestMapping("/updateUserShipping")
    public String updateShippingAddress(
            @ModelAttribute("id") Long shippingAddressId, Principal principal,
            Model model
    ) {

        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.findById(shippingAddressId);

        if (user.getId()!=userShipping.getUser().getId()) {
            return "badRequestPage";
        } else {

            model.addAttribute("user", user);
            model.addAttribute("userShipping", userShipping);



      //      List<String> stateList = USConstants.listOfUSStatesCode;
       //     Collections.sort(stateList);
        //    model.addAttribute("stateList", stateList);

            model.addAttribute("addNewShippingAddress", true);
            model.addAttribute("classActiveShipping", true);
            model.addAttribute("listOfCreditCards", true);
        //    model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());


            return "myProfile";
        }
    }

    @RequestMapping("/removeUserShipping")
    public String removeUserShipping(
            @ModelAttribute("id") Long userShippingId, Principal principal,
            Model model
    ) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        userShippingService.removeById(userShippingId);

        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("userShippingList", user.getUserShippingList());
     //   model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("listOfCreditCards", true);

        return "myProfile";
    }

    @RequestMapping("/badRequest")
    public String badRequest() {
        return "badRequestPage";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUser(HttpServletRequest request,
                          @ModelAttribute("email") String userEmail,
                          @ModelAttribute("username") String username,
                          Model model
    ) throws Exception {
        model.addAttribute("classActiveNewAccount", "true");
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);


//        check username exists
        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "myAccount";
        }

//        check email address exists
        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);

            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();

        SimpleMailMessage email =
                mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("emailSent", "true");

        return "myAccount";

    }

    @RequestMapping(value = "/user/addNewUser", method = RequestMethod.GET)
    public String addNewUser(
            Locale locale, Model model,
            @RequestParam("token") String token) {

        PasswordResetToken passToken = userService.getPasswordResetToken(token);
        if (passToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "Token has expired.");
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();

        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);

        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }



    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String profileInfo(
            @ModelAttribute("user") User user,
            @ModelAttribute("newPassword") String newPassword,
            Model model
    ) throws Exception {

        User currentUser = userService.findById(user.getId());

        if (currentUser == null) {
            throw new Exception("User not found");
        }

//      check email address exists
         if (userService.findByEmail(user.getEmail()) != null) {
            if (userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
                model.addAttribute("emailExists", "true");
                return "myProfile";
            }
        }

//        check username exists
        if (userService.findByUsername(user.getUsername()) != null) {
            if (userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
                model.addAttribute("usernameExists", "true");
                return "myProfile";
            }
        }

        SecurityConfig securityConfig = new SecurityConfig();

//      update password
        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.getPassword();
            if (passwordEncoder.matches(user.getPassword(), dbPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                model.addAttribute("incorrectPassword", true);

                return "myProfile";
            }
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.save(currentUser);

        model.addAttribute("updateSuccess", "true");
        model.addAttribute("user", currentUser);
        model.addAttribute("classActiveEdit", true);


        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "myProfile";
    }
}
