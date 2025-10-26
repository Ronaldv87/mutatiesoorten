package com.ns.mutatiesoorten.service;

import com.ns.mutatiesoorten.model.MutatieSoorten;
import com.ns.mutatiesoorten.model.Traject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.ns.mutatiesoorten.model.MutatieSoorten.ORIGINELE_TRAJECT;
import static com.ns.mutatiesoorten.model.MutatieSoorten.ONBEKEND;
import static com.ns.mutatiesoorten.model.MutatieSoorten.OPHEFFEN_BEGIN;
import static com.ns.mutatiesoorten.model.MutatieSoorten.OPHEFFEN_EIND;
import static com.ns.mutatiesoorten.model.MutatieSoorten.VERLENGEN_BEGIN;
import static com.ns.mutatiesoorten.model.MutatieSoorten.VERLENGEN_EIND;

@Service
@AllArgsConstructor
public class MutatieDetector {

    public MutatieSoorten detecteerMutatieSoort(Traject oorspronkelijkTraject, Traject nieuwTraject) {
        var oospronkelijkeDrp = oorspronkelijkTraject.dienstregelpunten();
        var nieuweDrp = nieuwTraject.dienstregelpunten();

        if (nieuweDrp.isEmpty() || oospronkelijkeDrp.isEmpty()) return ONBEKEND;

        if (oospronkelijkeDrp.equals(nieuweDrp)) return ORIGINELE_TRAJECT;

        if (isVerlengenBegin(oospronkelijkeDrp, nieuweDrp)) return VERLENGEN_BEGIN;

        if (isVerlengenEind(oospronkelijkeDrp, nieuweDrp)) return VERLENGEN_EIND;

        if (isOpheffenBegin(oospronkelijkeDrp, nieuweDrp)) return OPHEFFEN_BEGIN;

        if (isOpheffenEind(oospronkelijkeDrp, nieuweDrp)) return OPHEFFEN_EIND;

        return ONBEKEND;
    }

    private boolean isVerlengenBegin(List<String> oospronkelijkeDrp, List<String> nieuweDrp) {
        if (nieuweDrp.size() <= oospronkelijkeDrp.size()) return false;
        return IntStream.range(0, oospronkelijkeDrp.size())
                        .allMatch(i -> oospronkelijkeDrp.reversed().get(i).equals(nieuweDrp.reversed().get(i)));
    }

    private boolean isVerlengenEind(List<String> oospronkelijkeDrp, List<String> nieuweDrp) {
        if (nieuweDrp.size() <= oospronkelijkeDrp.size()) return false;
        return IntStream.range(0, oospronkelijkeDrp.size())
                        .allMatch(i -> oospronkelijkeDrp.get(i).equals(nieuweDrp.get(i)));
    }

    private boolean isOpheffenBegin(List<String> oospronkelijkeDrp, List<String> nieuweDrp) {
        if (nieuweDrp.size() >= oospronkelijkeDrp.size()) return false;
        return IntStream.range(0, nieuweDrp.size())
                        .allMatch(i -> oospronkelijkeDrp.reversed().get(i).equals(nieuweDrp.reversed().get(i)));
    }

    private boolean isOpheffenEind(List<String> oospronkelijkeDrp, List<String> nieuweDrp) {
        if (nieuweDrp.size() >= oospronkelijkeDrp.size()) return false;
        return IntStream.range(0, nieuweDrp.size())
                        .allMatch(i -> oospronkelijkeDrp.get(i).equals(nieuweDrp.get(i)));
    }
}
