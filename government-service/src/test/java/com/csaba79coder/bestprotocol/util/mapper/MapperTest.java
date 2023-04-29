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

/**
 * This class contains JUnit test cases for the Mapper class that is responsible for mapping various entity objects to
 * their respective models. The tests in this class verify that the Mapper class correctly maps entities to their expected
 * models.
 */
class MapperTest {

    /**
     * This test case verifies that the {@link Mapper#mapRepresentativeEntityToAdminModel} method correctly maps a
     * Representative entity object to its expected RepresentativeAdminModel. It creates a Representative entity object and sets
     * its properties with test data, then creates a RepresentativeAdminModel object with the same test data and sets its
     * properties as expected. It then calls the Mapper's mapRepresentativeEntityToAdminModel method and verifies that the
     * resulting model is not null and matches the expected model.
     */
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

    /**
     * This test case verifies that the {@link Mapper#mapGovernmentTranslationToAdminModel} method correctly maps a
     * GovernmentTranslation entity object to its expected GovernmentAdminModel. It creates a GovernmentTranslation entity
     * object and sets its properties with test data, then creates a GovernmentAdminModel object with the same test data and
     * sets its properties as expected. It then calls the Mapper's mapGovernmentTranslationToAdminModel method and verifies
     * that the resulting model is not null and matches the expected model.
     */
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
        then(model.getLanguageShortName()).isEqualTo("en");
        then(model.getName()).isEqualTo("Ministry of Finance");
        then(model)
                .isNotNull()
                .isEqualTo(expectedModel);
    }

    /**

     Test case for the {@link Mapper#mapGovernmentTranslationToGovernmentAdminModel(GovernmentTranslation)}

     method, which should map a {@link GovernmentTranslation} entity to a {@link GovernmentTranslationModel}

     with the same language short name and name properties.

     The test creates a sample {@link GovernmentTranslation} entity and a {@link GovernmentTranslationModel}

     with the expected properties, and passes the entity to the mapper method. The resulting model is then

     compared to the expected model using assertions from the AssertJ library.

     If the mapping is correct, the resulting model should have the same language short name and name properties

     as the original entity, and all other properties should be null or default.
     */
    @Test
    @DisplayName("shouldMapGovernmentTranslationToGovernmentTranslationModel")
    void shouldMapGovernmentTranslationToGovernmentTranslationModel(){
        // Given
        GovernmentTranslation translation = new GovernmentTranslation();
        translation.setLanguageShortName("en");
        translation.setName("Ministry of Finance");
        GovernmentTranslationModel expectedModel = new GovernmentTranslationModel()
                .languageShortName("en")
                .name("Ministry of Finance");


        // When
        GovernmentTranslationModel model = Mapper.mapGovernmentTranslationToGovernmentAdminModel(translation);

        // Then
        then(model.getLanguageShortName()).isEqualTo("en");
        then(model.getName()).isEqualTo("Ministry of Finance");
        then(model)
                .isNotNull()
                .isEqualTo(expectedModel);
    }

    /**
     * This test case verifies that the {@link Mapper#mapMenuTranslationEntityToModel} method correctly maps a MenuTranslation
     * entity object to its expected MenuTranslationModel. It creates a MenuTranslation entity object and sets its properties
     * with test data, then creates a MenuTranslationModel object with the same test data and sets its properties as expected.
     * It then calls the Mapper's mapMenuTranslationEntityToModel method and verifies that the resulting model is not null
     * and matches the expected model.
     */
    @Test
    @DisplayName("testMapMenuTranslationEntityToModel")
    void testMapMenuTranslationEntityToModel(){
        // Given
        MenuTranslation entity = new MenuTranslation();
        entity.setLanguageShortName("hu");
        entity.setTranslationValue("all");
        entity.setTranslationValue("All");

        MenuTranslationModel expectedModel = new MenuTranslationModel()
                .languageShortName("hu")
                .translationKey("all")
                .translationValue("All");
        
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