package com.app.djitrack.service;

import com.app.djitrack.dto.PaiementRequest;
import com.app.djitrack.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final OrangeMoneyService orangeMoneyService;
    private final MoovMoneyService moovMoneyService;

    public String payerOrange(
            PaiementRequest request){

        return orangeMoneyService.payer(
                request.getMontant(),
                UUID.randomUUID().toString());
    }

    public String payerMoov(
            PaiementRequest request){

        return moovMoneyService.payer(
                request.getMontant(),
                UUID.randomUUID().toString());
    }
}