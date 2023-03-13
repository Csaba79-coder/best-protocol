package com.csaba79coder.bestprotocol.util.mapper;

import com.csaba79coder.bestprotocol.model.NewRepresentativeModel;
import com.csaba79coder.bestprotocol.model.RepresentativeModel;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.util.ImageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Representative mapFieldIntoEntity(String name, String jobTitle, String government, String address, String phoneNumber, String email, MultipartFile image, String note) {
        Representative entity = new Representative();
        entity.setName(name);
        entity.setJobTitle(jobTitle);
        entity.setGovernment(government);
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
        entity.setNote(note);
        return entity;
    }

    public static RepresentativeModel mapRepresentativeEntityToModel(Representative entity) {
        return new RepresentativeModel()
                .id(entity.getId())
                .createdAt(String.valueOf(entity.getCreatedAt()))
                .updatedAt(String.valueOf(entity.getUpdatedAt()))
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .name(entity.getName())
                .jobTitle(entity.getJobTitle())
                .government(entity.getGovernment())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .image(ImageUtil.decompressImage(entity.getImage()))
                .note(entity.getNote());
    }

    public static Representative mapNewRepresentativeModelToEntity(NewRepresentativeModel model) {
        Representative entity = new Representative();
        modelMapper.map(model, entity);
        return entity;
    }
}
