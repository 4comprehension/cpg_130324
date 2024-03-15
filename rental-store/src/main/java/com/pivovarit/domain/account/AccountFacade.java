package com.pivovarit.domain.account;

import com.pivovarit.domain.rental.RentalFacade;
import com.pivovarit.domain.warehouse.WarehouseFacade;

public class AccountFacade {

    private final RentalFacade rentalFacade;
    private final WarehouseFacade warehouseFacade;

    public AccountFacade(RentalFacade rentalFacade, WarehouseFacade warehouseFacade) {
        this.rentalFacade = rentalFacade;
        this.warehouseFacade = warehouseFacade;
    }

    public boolean canUserRent(int id) {
        return true;
    }
}
