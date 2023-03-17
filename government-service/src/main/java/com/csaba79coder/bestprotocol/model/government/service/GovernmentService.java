package com.csaba79coder.bestprotocol.model.government.service;

import com.csaba79coder.bestprotocol.model.GovernmentAdminModel;
import com.csaba79coder.bestprotocol.model.government.persistence.GovernmentRepository;
import com.csaba79coder.bestprotocol.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GovernmentService {

    private final GovernmentRepository governmentRepository;

    public List<GovernmentAdminModel> findAllGovernments() {
        return governmentRepository.findAll()
                .stream()
                .map(Mapper::mapGovernmentEntityToAdminModel)
                .collect(Collectors.toList());
    }
}
