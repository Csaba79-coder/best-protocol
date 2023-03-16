package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.RepresentativeAdminModel;
import com.csaba79coder.bestprotocol.model.representative.entity.Government;
import com.csaba79coder.bestprotocol.model.representative.persistence.GovernmentRepository;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final GovernmentRepository governmentRepository;

    public RepresentativeAdminModel addNewRepresentative(String name, String jobTitle, String government, String address, String phoneNumber, String email, MultipartFile image, String note) {
        return Mapper.mapRepresentativeEntityToAdminModel(representativeRepository.save(Mapper.mapFieldIntoEntity(name, jobTitle, government, address, phoneNumber, email, image, note)));
    }

    public List<RepresentativeAdminModel> renderAllRepresentatives() {
        return representativeRepository.findAll()
                .stream()
                .map(Mapper::mapRepresentativeEntityToAdminModel)
                .collect(Collectors.toList());
    }

    /*public List<RepresentativeAdminModel> renderAllRepresentativesByGovernmentId(Long governmentId, Integer page, Integer size) {
        return representativeRepository.findByGovernmentId(governmentId, PageRequest.of(page, size))
                .stream().toList();
    }*/


    public Government findGovernmentByName(String government) {
        return governmentRepository.findGovernmentByNameContainsIgnoreCase(government)
                .orElseThrow(() -> {
                    String message = String.format("Government: %s was not found", government);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public List<RepresentativeAdminModel> renderAllRepresentativesByGovernmentId(Long governmentId) {
        return representativeRepository.findRepresentativeByGovernmentId(governmentId)
                .stream()
                .map(Mapper::mapRepresentativeEntityToAdminModel)
                .collect(Collectors.toList());
    }
}
