package com.app.djitrack.service;

import org.springframework.stereotype.Service;

@Service
public class MoovMoneyService {

    public String payer(
            Double montant,
            String reference){

        return "Paiement Moov Money validé";
    }
}
