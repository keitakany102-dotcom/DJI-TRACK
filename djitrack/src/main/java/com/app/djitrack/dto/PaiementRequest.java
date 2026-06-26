package com.app.djitrack.dto;

import lombok.Data;

@Data
public class PaiementRequest {

    private Long factureId;

    private Double montant;

    private String mode;
}