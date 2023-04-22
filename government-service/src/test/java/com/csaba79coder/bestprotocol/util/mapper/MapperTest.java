package com.csaba79coder.bestprotocol.util.mapper;

import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.menu.entity.MenuTranslation;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.model.value.Availability;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

class MapperTest {


    @Test
    @DisplayName("shouldMapRepresentativeEntityToAdminModel")
    void shouldMapRepresentativeEntityToAdminModel(){
        // Given
        String testString = "Hello, world!";
        byte[] testData = testString.getBytes();

        Representative entity = new Representative();
        entity.setPhoneNumber("+36-30...");
        entity.setEmail("test@test.com");
        entity.setImage(testData);
        entity.setAvailability(Availability.AVAILABLE);

        RepresentativeAdminModel expectedModel = new RepresentativeAdminModel()
                .availability(com.csaba79coder.bestprotocol.model.Availability.AVAILABLE)
                .email("test@test.com")
                .image(testData)
                .createdBy(UUID.fromString("6772c9dc-a7be-4826-963a-e376074fd4e7"))
                .updatedBy(UUID.fromString("dbd58012-9ee7-47d5-8f87-9bbc91583009"))
                .phoneNumber("+36-30...");

        // When
        RepresentativeAdminModel model = Mapper.mapRepresentativeEntityToAdminModel(entity);

        // Then
        then(model)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "government",
                        "representativeTranslation", "previousJobTitle", "image")
                .isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("shouldMapGovernmentTranslationToAdminModel")
    void shouldMapGovernmentTranslationToAdminModel(){
        // Given
        GovernmentTranslation translation = new GovernmentTranslation();
        translation.setLanguageShortName("en");
        translation.setName("Ministry of Finance");
        GovernmentAdminModel expectedModel = new GovernmentAdminModel()
                .languageShortName("en")
                .name("Ministry of Finance");

        // When
        GovernmentAdminModel model = Mapper.mapGovernmentTranslationToAdminModel(translation);

        // Then
        then(model).isNotNull();
        then(model.getLanguageShortName()).isEqualTo("en");
        then(model.getName()).isEqualTo("Ministry of Finance");
        then(model)
                .isEqualTo(expectedModel);
    }

    @Test
    @DisplayName("shouldMapGovernmentTranslationToGovernmentTranslationModel")
    void shouldMapGovernmentTranslationToGovernmentTranslationModel(){
        // Given
        GovernmentTranslation translation = new GovernmentTranslation();
        translation.setLanguageShortName("il");
        translation.setName("מִשְׂרָד רֹאשׁ הַמֶּמְשָׁלָה");
        GovernmentTranslationModel expectedModel = new GovernmentTranslationModel()
                .languageShortName("il")
                .name("מִשְׂרָד רֹאשׁ הַמֶּמְשָׁלָה");


        // When
        GovernmentTranslationModel model = Mapper.mapGovernmentTranslationToGovernmentAdminModel(translation);

        // Then
        then(model.getLanguageShortName()).isEqualTo("il");
        then(model.getName()).isEqualTo("מִשְׂרָד רֹאשׁ הַמֶּמְשָׁלָה");
        then(model)
                .isEqualTo(expectedModel);
    }
    
    // testMapMenuTranslationEntityToModel()
    @Test
    @DisplayName("testMapMenuTranslationEntityToModel")
    void testMapMenuTranslationEntityToModel(){
        // Given
        MenuTranslation entity = new MenuTranslation();
        entity.setLanguageShortName("hu");
        entity.setTranslationValue("all");
        entity.setTranslationValue("Összes");

        MenuTranslationModel expectedModel = new MenuTranslationModel()
                .languageShortName("hu")
                .translationKey("all")
                .translationValue("Összes");
        
        // When
        MenuTranslationModel model = Mapper.mapMenuTranslationEntityToModel(entity);

        // Then
        then(model)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "translationKey")
                .isEqualTo(expectedModel);
    }
}