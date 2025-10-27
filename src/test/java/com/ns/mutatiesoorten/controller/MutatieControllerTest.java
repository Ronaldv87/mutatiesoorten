package com.ns.mutatiesoorten.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.mutatiesoorten.dto.MutatieRequest;
import com.ns.mutatiesoorten.exceptions.MutatieRequestExceptie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MutatieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void verwerkMutatie_geeftVerlengenBeginTerug_bijOpgevenMutatie() throws Exception {
        // GIVEN
        var oorspronkelijkeTraject = List.of("A", "B", "C", "D", "E");
        var nieuweTraject = List.of("X", "Y", "Z", "A", "B", "C", "D", "E");
        MutatieRequest request = new MutatieRequest(oorspronkelijkeTraject, nieuweTraject);

        // WHEN THEN
        mockMvc.perform(post("/api/v1/mutaties/mutatie")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(content().string("VERLENGEN_BEGIN"));
    }

    @Test
    void verwerkMutatie_gooitExceptie_bijDeelsLeegRequest() throws Exception {
        // GIVEN
        var oorspronkelijkeTraject = List.of("A", "B", "C", "D", "E");
        MutatieRequest request = new MutatieRequest(oorspronkelijkeTraject, List.of());

        // WHEN THEN
        mockMvc.perform(post("/api/v1/mutaties/mutatie")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(result -> {
                   assertInstanceOf(MutatieRequestExceptie.class, result.getResolvedException());
                   assertThat(result.getResolvedException()
                                    .getMessage()
                                    .equals("Dienstregelpunten lijsten mogen niet leeg zijn."));
               });
    }
}
