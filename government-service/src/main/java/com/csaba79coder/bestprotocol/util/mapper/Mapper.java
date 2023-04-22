package com.csaba79coder.bestprotocol.util.mapper;

import com.csaba79coder.bestprotocol.model.Availability;
import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.GovernmentTranslationModel;
import com.csaba79coder.bestprotocol.model.MenuTranslationModel;
import com.csaba79coder.bestprotocol.model.NewRepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.government.entity.Government;
import com.csaba79coder.bestprotocol.model.government.entity.GovernmentTranslation;
import com.csaba79coder.bestprotocol.model.menu.entity.MenuTranslation;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.util.ImageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mapper {

    // TODO adding secretairat field

    private static final ModelMapper modelMapper = new ModelMapper();

    // TODO write test!
    public static Representative mapFieldIntoEntity(String languageShortName, String name, String jobTitle, String government, String secretairat, String address, String phoneNumber, String email, MultipartFile image, String note) {
        Representative entity = new Representative();
        /*entity.setName(name);
        entity.setJobTitle(jobTitle);*/
        // TODO check it! 
        // entity.setGovernment(representativeService.findGovernmentByName(government));
        /*entity.setSecretairat(secretairat);
        entity.setAddress(address);*/
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
    }

    // Test done!
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

    public static Representative mapNewRepresentativeAdminModelToEntity(NewRepresentativeAdminModel model) {
        Representative entity = new Representative();
        modelMapper.map(model, entity);
        return entity;
    }

    public static GovernmentAdminModel mapGovernmentEntityToAdminModel(Government entity) {
        GovernmentAdminModel model = new GovernmentAdminModel();
        modelMapper.map(entity, model);
        return model;
    }

    // Test done!
    public static GovernmentAdminModel mapGovernmentTranslationToAdminModel(GovernmentTranslation translation) {
        GovernmentAdminModel model = new GovernmentAdminModel();
        modelMapper.map(translation, model);
        return model;
    }

    // Test done!
    public static GovernmentTranslationModel mapGovernmentTranslationToGovernmentAdminModel(GovernmentTranslation translation) {
        GovernmentTranslationModel model = new GovernmentTranslationModel();
        modelMapper.map(translation, model);
        return model;
    }

    // Test done!
    public static MenuTranslationModel mapMenuTranslationEntityToModel(MenuTranslation entity) {
        MenuTranslationModel model = new MenuTranslationModel();
        modelMapper.map(entity, model);
        return model;
    }
}
