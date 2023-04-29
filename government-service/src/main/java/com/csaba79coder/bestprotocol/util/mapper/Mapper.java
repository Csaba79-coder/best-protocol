package com.csaba79coder.bestprotocol.util.mapper;

import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.menu.entity.MenuTranslation;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.util.ImageUtil;
import org.modelmapper.ModelMapper;

/**

 The Mapper class is a utility class used for mapping between entities and models,
 as well as mapping individual fields between them. This class provides methods for
 mapping an entity to its corresponding model, and vice versa, as well as methods for
 mapping individual fields between them.
 This class uses the MapStruct library to generate the necessary mapping code based
 on annotations in the source code. MapStruct is a code generation library that allows
 for easy and efficient mapping between objects, with minimal boilerplate code.
 */
public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    // TODO write test!
    // TODO implement function for creating new Representative entity from the incoming fields!
    // TODO update the api.yaml (e.g. name is prefix, firstname, midname, lastname, suffix) -> so can created English format from it!
    // English or Hebrew name can be only created after we have id
    // (so when entity saved to db) -> so we need to update the entity after saving it to db regarding the languageShortName!
    /*public static Representative mapFieldIntoEntity(String languageShortName, String name, String jobTitle, String government, String secretairat, String address, String phoneNumber, String email, MultipartFile image, String note) {
        Representative entity = new Representative();
        entity.setName(name);
        entity.setJobTitle(jobTitle);
        entity.setGovernment(representativeService.findGovernmentByName(government));
        entity.setSecretairat(secretairat);
        entity.setAddress(address);
        entity.setPhoneNumber(phoneNumber);
        entity.setEmail(email);
        if (image.isEmpty()) {
            File file = new File("government-service/src/main/resources/static/images/placeholder.png");
            try {
                FileInputStream inputStream = new FileInputStream(file);
                entity.setImage(ImageUtil.compressImage(inputStream.readAllBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                entity.setImage(ImageUtil.compressImage(image.getBytes()));
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        }
        // entity.setNote(note);
        return entity;
    }*/

    // Test done!
    /**
     * Maps the given Representative entity to a RepresentativeAdminModel and returns the resulting model.
     *
     * @param entity the Representative entity to map
     * @return a RepresentativeAdminModel object containing the mapped data
     */
    public static RepresentativeAdminModel mapRepresentativeEntityToAdminModel(Representative entity) {
        return new RepresentativeAdminModel()
                .id(entity.getId())
                .createdAt(String.valueOf(entity.getCreatedAt()))
                .updatedAt(String.valueOf(entity.getUpdatedAt()))
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .image(ImageUtil.decompressImage(entity.getImage()))
                .availability(Availability.valueOf(entity.getAvailability().name()));
    }

    // TODO it is not used yet! it will be needed when adding new Representative is implemented
    /*public static Representative mapNewRepresentativeAdminModelToEntity(NewRepresentativeAdminModel model) {
        Representative entity = new Representative();
        modelMapper.map(model, entity);
        return entity;
    }*/

    // TODO it is not used yet! it will be needed when adding new Government function is implemented
    /*public static GovernmentAdminModel mapGovernmentEntityToAdminModel(Government entity) {
        GovernmentAdminModel model = new GovernmentAdminModel();
        modelMapper.map(entity, model);
        return model;
    }*/

    // Test done!
    /**
     * Maps the given GovernmentTranslation object to a GovernmentAdminModel and returns the resulting model.
     *
     * @param translation the GovernmentTranslation object to map
     * @return a GovernmentAdminModel object containing the mapped data
     */
    public static GovernmentAdminModel mapGovernmentTranslationToAdminModel(GovernmentTranslation translation) {
        GovernmentAdminModel model = new GovernmentAdminModel();
        modelMapper.map(translation, model);
        return model;
    }

    // Test done!
    /**
     * Maps the given GovernmentTranslation object to a GovernmentTranslationModel and returns the resulting model.
     *
     * @param translation the GovernmentTranslation object to map
     * @return a GovernmentTranslationModel object containing the mapped data
     */
    public static GovernmentTranslationModel mapGovernmentTranslationToGovernmentAdminModel(GovernmentTranslation translation) {
        GovernmentTranslationModel model = new GovernmentTranslationModel();
        modelMapper.map(translation, model);
        return model;
    }

    // Test done!
    /**
     * Maps the given MenuTranslation entity to a MenuTranslationModel and returns the resulting model.
     *
     * @param entity the MenuTranslation entity to map
     * @return a MenuTranslationModel object containing the mapped data
     */
    public static MenuTranslationModel mapMenuTranslationEntityToModel(MenuTranslation entity) {
        MenuTranslationModel model = new MenuTranslationModel();
        modelMapper.map(entity, model);
        return model;
    }

    // as all the methods are static, I don't need to instantiate this class! to achieve this, I need to make the constructor private
    private Mapper() {
    }
}
