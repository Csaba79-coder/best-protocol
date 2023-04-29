package com.csaba79coder.bestprotocol;

import com.csaba79coder.bestprotocol.controller.MenuTranslationController;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.menu.service.MenuTranslationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**

 Integration test for {@link MenuTranslationController}.
 */
@ExtendWith(MockitoExtension.class)
public class MenuTranslationControllerIT {

    private MockMvc mockMvc;

    @Mock
    private MenuTranslationService menuTranslationService;

    @InjectMocks
    private MenuTranslationController menuTranslationController;

    /**

     Set up the {@link MockMvc} instance before each test.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuTranslationController).build();
    }

    /**

     Test the behavior of the {@link MenuTranslationController#renderAllMenuTranslations(String, String)}

     method by sending a GET request to the '/en/api/admin/governments/menu' endpoint with valid request parameters.

     Expects the response status to be 'OK' (200) and the response content to match the expected value.

     @throws Exception if an error occurs while performing the test.
     */
    @Test
    @DisplayName("givenLanguageShortNameAndTranslationKey_whenGetAllMenuTranslations_thenReturnValidResponse")
    public void givenLanguageShortNameAndTranslationKey_whenGetAllMenuTranslations_thenReturnValidResponse() throws Exception {
        // Given
        String languageShortName = "en";
        String translationKey = "menu.home";

        List<MenuTranslationModel> expectedMenuTranslations = Arrays.asList(
                new MenuTranslationModel()
                        .id(1L)
                        .languageShortName("en")
                        .translationKey("menu.home")
                        .translationValue("Home"),
                new MenuTranslationModel()
                        .id(2L)
                        .languageShortName("en")
                        .translationKey("menu.about")
                        .translationValue("About")
        );

        given(menuTranslationService.findAllMenuTranslationByLanguage(languageShortName, translationKey))
                .willReturn(expectedMenuTranslations);

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/en/api/admin/governments/menu")
                        .param("languageShortName", languageShortName)
                        .param("translationKey", translationKey))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = mvcResult.getResponse().getContentAsString();

        List<MenuTranslationModel> actualMenuTranslations = new ObjectMapper().readValue(responseContent,
                new TypeReference<>() {
                });

        then(actualMenuTranslations)
                .isNotNull()
                .hasSize(expectedMenuTranslations.size())
                .containsExactlyElementsOf(expectedMenuTranslations);
    }
}
