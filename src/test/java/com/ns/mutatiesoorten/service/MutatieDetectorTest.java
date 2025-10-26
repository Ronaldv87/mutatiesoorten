package com.ns.mutatiesoorten.service;

import com.ns.mutatiesoorten.model.Traject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ns.mutatiesoorten.model.MutatieSoorten.ORIGINELE_TRAJECT;
import static com.ns.mutatiesoorten.model.MutatieSoorten.ONBEKEND;
import static com.ns.mutatiesoorten.model.MutatieSoorten.OPHEFFEN_BEGIN;
import static com.ns.mutatiesoorten.model.MutatieSoorten.VERLENGEN_BEGIN;
import static com.ns.mutatiesoorten.model.MutatieSoorten.VERLENGEN_EIND;
import static org.assertj.core.api.Assertions.assertThat;

class MutatieDetectorTest {

    private MutatieDetector mutatieDetector;

    @BeforeEach
    void setUp() {
        mutatieDetector = new MutatieDetector();
    }

    @Test
    void detecteerMutatieSoort_geeftGeenMutatie_alsTrajectenGelijkZijn() {
        // Arrange
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("A", "B", "C", "D", "E"));

        // Act
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Assert
        assertThat(mutatieSoort).isEqualTo(ORIGINELE_TRAJECT);
    }

    @Test
    void detecteerMutatieSoort_geeftOnbekend_alsOorspronkelijkeTrajectLeegIs() {
        // Arrange
        var oorspronkelijkTraject = new Traject(List.of());
        var nieuwTraject = new Traject(List.of("A", "B", "C", "D", "E"));

        // Act
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Assert
        assertThat(mutatieSoort).isEqualTo(ONBEKEND);
    }

    @Test
    void detecteerMutatieSoort_geeftOnbekend_alsNieuwTrajectLeegIs() {
        // Arrange
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of());

        // Act
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Assert
        assertThat(mutatieSoort).isEqualTo(ONBEKEND);
    }

    @Test
    void detecteerMutatieSoort_geeftOnbekend_alsTrajectenNietGelijkZijn() {
        // Given
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("A", "B", "C", "D", "F"));

        // When
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Then
        assertThat(mutatieSoort).isEqualTo(ONBEKEND);
    }

    @Test
    void detecteerMutatieSoort_geeftVerlengenBegin_alsTrajectBeginIsVerlengd() {
        // Given
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("X", "Y", "Z", "A", "B", "C", "D", "E"));

        // When
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Then
        assertThat(mutatieSoort).isEqualTo(VERLENGEN_BEGIN);
    }

    @Test
    void detecteerMutatieSoort_geeftVerlengenEind_alsTrajectBeginIsVerlengd() {
        // Given
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("A", "B", "C", "D", "E", "X", "Y", "Z"));

        // When
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Then
        assertThat(mutatieSoort).isEqualTo(VERLENGEN_EIND);
    }

    @Test
    void detecteerMutatieSoort_geeftOpheffenBegin_alsTrajectBeginIsOpgeheven() {
        // Given
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("C", "D", "E"));

        // When
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Then
        assertThat(mutatieSoort).isEqualTo(OPHEFFEN_BEGIN);
    }

    @Test
    void detecteerMutatieSoort_geeftOnbekend_alsMeerdereMutatiesUitgevoerdZijn() {
        // Given
        var oorspronkelijkTraject = new Traject(List.of("A", "B", "C", "D", "E"));
        var nieuwTraject = new Traject(List.of("C", "D", "E", "X", "Y", "Z"));

        // When
        var mutatieSoort = mutatieDetector.detecteerMutatieSoort(oorspronkelijkTraject, nieuwTraject);

        // Then
        assertThat(mutatieSoort).isEqualTo(ONBEKEND);
    }
}