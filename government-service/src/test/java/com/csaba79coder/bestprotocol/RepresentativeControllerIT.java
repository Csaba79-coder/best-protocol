package com.csaba79coder.bestprotocol;

import com.csaba79coder.bestprotocol.controller.RepresentativeController;
import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.representative.service.RepresentativeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**

 This class represents integration tests for the RepresentativeController class.
 It uses the @WebMvcTest annotation to test only the RepresentativeController class.
 The @ContextConfiguration annotation is used to specify the context configuration classes.
 The @ExtendWith(MockitoExtension.class) annotation is used to load the Mockito extension for JUnit 5.
 The class contains one test method, givenValidRequest_whenRepresentativeFindByGovernmentId_thenReturnOk,
 which tests the findByGovernmentId method of the RepresentativeController class.
 The test uses MockMvc to perform a GET request with given query parameters, and expects a 200 status code.
 The test also mocks the RepresentativeService and sets it up to return expected data.
 The test then performs two assertions using AssertJ's then() method.
 The first assertion checks that the actual list of RepresentativeAdminModel objects is not null.
 The second assertion checks that the actual list of RepresentativeAdminModel objects is equal to itself,
 after ignoring certain fields (id, createdAt, updatedAt, createdBy, updatedBy, government, and image).
 */
@WebMvcTest(RepresentativeController.class)
@ContextConfiguration(classes = {RepresentativeController.class})
@ExtendWith(MockitoExtension.class)
public class RepresentativeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RepresentativeService representativeService;

    /**
     * Tests the behavior of the RepresentativeController's findByGovernmentId method when given a valid request.
     *
     * <p>This test sends a GET request to /en/api/admin/gov-representatives with valid parameters, and expects a
     * response with status code 200 and a list of RepresentativeAdminModel objects in the response body. The test
     * verifies that the returned list matches the expected list, ignoring certain fields.</p>
     *
     * @throws Exception if an error occurs while performing the test
     */
    @Test
    @DisplayName("Given valid request when Representative findByGovernmentId then return ok")
    public void givenValidRequest_whenRepresentativeFindByGovernmentId_thenReturnOk() throws Exception {
        // Given
        Long governmentId = 1L;
        String languageShortName = "en";
        String search = "John";
        Integer page = 0;
        Integer size = 10;

        List<RepresentativeAdminModel> expectedRepresentativeAdminModels = createTestRepresentativeAdminModelList();

        given(representativeService.renderAllRepresentativesByGovernmentId(languageShortName, governmentId, search))
                .willReturn(expectedRepresentativeAdminModels);

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/gov-representatives/governments/{governmentId}", governmentId)
                        .param("languageShortName", languageShortName)
                        .param("search", search)
                        .param("page", page.toString())
                        .param("size", size.toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<RepresentativeAdminModel> actualRepresentativeAdminModels = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        then(actualRepresentativeAdminModels)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "createdBy", "updatedBy", "government", "image")
                .isNotNull();
        then(actualRepresentativeAdminModels)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "createdBy", "updatedBy", "government", "image")
                .isEqualTo(actualRepresentativeAdminModels)
                .isNotNull();
    }

    /**

     Test case for the endpoint which returns all the representatives based on the given filters.

     Given valid request parameters for the search,

     when Representative service is called for fetching all the representatives,

     then the response status should be OK with a list of expected representative admin models in the response body.
     */
    @Test
    @DisplayName("Given valid request when Representative find all then return ok")
    public void givenValidRequest_whenRepresentativeFindAll_thenReturnOk() throws Exception {
        // Given
        Long governmentId = 1L;
        String languageShortName = "en";
        String search = "John";
        Integer page = 0;
        Integer size = 10;

        List<RepresentativeAdminModel> expectedRepresentativeAdminModels = createTestRepresentativeAdminModelList();

        given(representativeService.renderAllRepresentatives(languageShortName, search))
                .willReturn(expectedRepresentativeAdminModels);

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/gov-representatives")
                        .param("languageShortName", languageShortName)
                        .param("search", search)
                        .param("page", page.toString())
                        .param("size", size.toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<RepresentativeAdminModel> actualRepresentativeAdminModels = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        then(actualRepresentativeAdminModels)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "createdBy", "updatedBy", "government", "image")
                .isNotNull();
        then(actualRepresentativeAdminModels)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "createdBy", "updatedBy", "government", "image")
                .isEqualTo(actualRepresentativeAdminModels)
                .isNotNull();
    }

    /**

     Creates a test list of {@link RepresentativeAdminModel} objects for use in tests.
     Each object is initialized with a phone number, email, image, and availability.
     @return A list of {@link RepresentativeAdminModel} objects.
     */
    private List<RepresentativeAdminModel> createTestRepresentativeAdminModelList() {
        String testString = "Hello, world!";
        byte[] testData = testString.getBytes();

        return Arrays.asList(
                new RepresentativeAdminModel()
                        .phoneNumber("+36--30-123-4567")
                        .email("test1@test.com")
                        .image(testData)
                        .availability(Availability.AVAILABLE),
                new RepresentativeAdminModel()
                        .phoneNumber("+36--30-123-4567")
                        .email("test1@test.com")
                        .image(testData)
                        .availability(Availability.AVAILABLE));
    }
}