package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.RepresentativeModel;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;

    public RepresentativeModel addNewRepresentative(String name, String jobTitle, String government, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return Mapper.mapRepresentativeEntityToModel(representativeRepository.save(Mapper.mapFieldIntoEntity(name, jobTitle, government, address, phoneNumber, email, image, note)));
    }

    public List<RepresentativeModel> renderAllRepresentatives() {
        return representativeRepository.findAll()
                .stream()
                .map(Mapper::mapRepresentativeEntityToModel)
                .collect(Collectors.toList());
    }
}
