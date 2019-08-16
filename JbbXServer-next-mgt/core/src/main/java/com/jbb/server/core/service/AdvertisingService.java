package com.jbb.server.core.service;

import java.util.List;

import com.jbb.server.core.domain.Advertising;

public interface AdvertisingService {
    Advertising getAdById(Integer adId);

    List<Advertising> getAdvertising(String Platform);
}
