package com.tugasakhir.resource;

import com.tugasakhir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserResources {

    @Autowired
    private UserService userService;


}
