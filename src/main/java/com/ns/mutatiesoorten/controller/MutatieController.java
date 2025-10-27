package com.ns.mutatiesoorten.controller;

import com.ns.mutatiesoorten.dto.MutatieRequest;
import com.ns.mutatiesoorten.exceptions.MutatieRequestExceptie;
import com.ns.mutatiesoorten.mapper.TrajectMapper;
import com.ns.mutatiesoorten.service.MutatieDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mutaties")
public class MutatieController {

    private final TrajectMapper trajectMapper;
    private final MutatieDetector mutatieDetector;

    @Autowired
    public MutatieController(TrajectMapper trajectMapper, MutatieDetector mutatieDetector) {
        this.trajectMapper = trajectMapper;
        this.mutatieDetector = mutatieDetector;
    }


    @PostMapping("/mutatie")
    public String verwerkMutatie(@RequestBody MutatieRequest mutatieRequest) {
        if (mutatieRequest.getOorspronkelijkeDienstregelpunten().isEmpty() || mutatieRequest.getNieuweDienstregelpunten().isEmpty()) {
            throw new MutatieRequestExceptie("Dienstregelpunten lijsten mogen niet leeg zijn.");
        }
        var oorspronkelijkTraject = trajectMapper.mapToOorspronkelijkeTraject(mutatieRequest);
        var nieuwTraject = trajectMapper.mapToNieuweTraject(mutatieRequest);
        return mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject).name();
    }
}

