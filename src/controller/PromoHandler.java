package controller;

import model.Promo;
import java.util.ArrayList;

// Handler untuk promo
public class PromoHandler {

    public Promo getPromo(String code) {
        return Promo.getPromoByCode(code);
    }

    public void addPromo(Promo promo) {
        if (promo.save()) {
        } else {
            System.out.println("Failed to add promo!");
        }
    }
}
