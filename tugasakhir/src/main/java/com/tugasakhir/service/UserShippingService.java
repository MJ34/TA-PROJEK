package com.tugasakhir.service;

import com.tugasakhir.domain.UserShipping;

public interface UserShippingService {

    UserShipping findById(Long id);

    void removeById(Long id);
}
