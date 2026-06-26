package com.app.djitrack.service;

import org.springframework.stereotype.Service;

@Service
public class OrangeMoneyService {

    public String payer(
            Double montant,
            String reference){

        return "Paiement Orange Money validé";
    }
}