package com.ns.mutatiesoorten.mapper;

import com.ns.mutatiesoorten.dto.MutatieRequest;
import com.ns.mutatiesoorten.model.Traject;
import org.springframework.stereotype.Component;

@Component
public class TrajectMapper {

    public Traject mapToOorspronkelijkeTraject(MutatieRequest mutatieRequest) {
        return new Traject(mutatieRequest.getOorspronkelijkeDienstregelpunten());
    }

    public Traject mapToNieuweTraject(MutatieRequest mutatieRequest) {
        return new Traject(mutatieRequest.getNieuweDienstregelpunten());
    }
}
