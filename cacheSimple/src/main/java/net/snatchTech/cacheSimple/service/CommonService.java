package net.snatchTech.cacheSimple.service;

import net.snatchTech.cacheSimple.model.CarOwner;

public interface CommonService {

    void setOwnerToCar(String number, String fullName);

    CarOwner getOwnerByNumber(String number);
}
