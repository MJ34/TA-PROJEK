package com.tugasakhir.service;

import com.tugasakhir.domain.ShippingAddress;
import com.tugasakhir.domain.UserShipping;

public interface ShippingAddressService {

    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
