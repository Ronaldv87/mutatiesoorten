package com.ns.mutatiesoorten.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MutatieRequest {
    private List<String> oorspronkelijkeDienstregelpunten;
    private List<String> nieuweDienstregelpunten;
}
