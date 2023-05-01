package com.csaba79coder.bestprotocol;

import com.csaba79coder.bestprotocol.controller.GovernmentController;
import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.government.service.GovernmentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Integration test class for the {@link GovernmentController}.
 * This class tests the behavior of the GovernmentController by sending mock HTTP requests
 * and asserting the responses returned by the controller methods.
 */
@WebMvcTest(GovernmentController.class)
@ContextConfiguration(classes = {GovernmentController.class})
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("resource")
public class GovernmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GovernmentService governmentService;

    private List<GovernmentTranslationModel> governmentTranslationModels;

    /**

     This method sets up the test environment before each test case. It initializes the mock objects using the openMocks() method from Mockito.
     It also creates an empty ArrayList of GovernmentTranslationModel and adds one model with languageShortName as "en" and name as "Ministry of Finance".
     */
    @BeforeEach
    void setUp() {
        openMocks(this);
        governmentTranslationModels = new ArrayList<>();
        governmentTranslationModels.add(new GovernmentTranslationModel()
                .languageShortName("en")
                .name("Ministry of Finance"));
    }

    /**
     * Tests the {@link GovernmentController#renderAllGovernments(String)} method by sending a GET request
     * to the "/en/api/admin/governments" endpoint with the "en" language parameter and verifying that
     * the response contains a list of government models with the expected properties.
     *
     * <p>This test uses the {@link MockMvc} API to send the HTTP request and to perform assertions on the
     * response. It also uses the {@link ObjectMapper} to serialize and deserialize JSON objects.</p>
     *
     * @throws Exception if an error occurs while sending the HTTP request or processing the response
     */
    @Test
    @DisplayName("should return all governments")
    void shouldReturnAllGovernments() throws Exception {
        // given
        when(governmentService.findAllGovernmentsByLangAndGovernmentId(anyString())).thenReturn(governmentTranslationModels);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/governments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ministry of Finance"));

        // then
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/governments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<GovernmentTranslationModel> result = objectMapper.readValue(responseBody, new TypeReference<>(){});
        then(result.size()).isEqualTo(1);
    }

    /**

     Tests the {@link GovernmentController#renderAllGovernmentsById


    (String, Long)} method by sending a GET request
     to the "/en/api/admin/governments/{id}" endpoint with the "en" language parameter and a government ID,
     and verifying that the response contains a list of government models with the expected properties.
     <p>This test uses the {@link MockMvc} API to send the HTTP request and to perform assertions on the
     response. It also uses the {@link ObjectMapper} to serialize and deserialize JSON objects.</p>
     @throws Exception if an error occurs while sending the HTTP request or processing the response
     */
    @Test
    @DisplayName("should return all governments by id")
    void shouldReturnAllGovernmentsById() throws Exception {
        // given
        String languageShortName = "en";
        Long governmentId = 1L;
        List<GovernmentTranslationModel> governmentTranslationModels = new ArrayList<>();
        governmentTranslationModels.add(new GovernmentTranslationModel()
                .languageShortName(languageShortName)
                .name("Ministry of Finance"));
        when(governmentService.findAllGovernmentsByLangAndGovernmentId(languageShortName, governmentId))
                .thenReturn(governmentTranslationModels);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/governments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ministry of Finance"));

        // then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/governments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();

        List<GovernmentTranslationModel> actualModels = objectMapper.readValue(responseBody, new TypeReference<>(){});
        List<GovernmentTranslationModel> expectedModels = governmentTranslationModels;

        then(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels);
    }
}
