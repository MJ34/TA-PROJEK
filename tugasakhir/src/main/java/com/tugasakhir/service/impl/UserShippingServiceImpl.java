package com.tugasakhir.service.impl;

import com.tugasakhir.domain.UserShipping;
import com.tugasakhir.repository.UserShippingRepository;
import com.tugasakhir.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShippingServiceImpl implements UserShippingService{

    @Autowired
    private UserShippingRepository userShippingRepository;

    public UserShipping findById(Long id) {
        return userShippingRepository.findOne(id);
    }

    public void removeById(Long id) {
        userShippingRepository.delete(id);
    }
}
