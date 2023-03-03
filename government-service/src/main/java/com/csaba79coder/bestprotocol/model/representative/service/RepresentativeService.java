package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import com.csaba79coder.model.RepresentativeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;

    public RepresentativeModel addNewRepresentative(String name, String jobTitle, String address, String phoneNumber, String email, MultipartFile image, String note) {
        Representative entity = Mapper.mapFieldIntoEntity(name, jobTitle, address, phoneNumber, email, image, note);
        return Mapper.mapRepresentativeEntityToModel(representativeRepository.save(entity));
    }

    public List<RepresentativeModel> renderAllRepresentatives() {
        return representativeRepository.findAll()
                .stream()
                .map(Mapper::mapRepresentativeEntityToModel)
                .collect(Collectors.toList());
    }
}
