package com.csaba79coder.bestprotocol.model.representative.service;

import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import com.csaba79coder.model.NewRepresentativeModel;
import com.csaba79coder.model.RepresentativeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;

    public RepresentativeModel addNewRepresentative(NewRepresentativeModel body) {
        return Mapper.mapRepresentativeEntityToModel(representativeRepository.save(Mapper.mapNewRepresentativeModelToEntity(body)));
    }

    public List<RepresentativeModel> renderAllRepresentatives() {
        return representativeRepository.findAll()
                .stream()
                .map(Mapper::mapRepresentativeEntityToModel)
                .collect(Collectors.toList());
    }
}
