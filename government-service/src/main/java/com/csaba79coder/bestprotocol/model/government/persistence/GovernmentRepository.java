package com.csaba79coder.bestprotocol.model.government.persistence;

import com.csaba79coder.bestprotocol.model.government.entity.Government;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentRepository extends JpaRepository<Government, Long> {
    // Optional<Government> findGovernmentByNameContainsIgnoreCase(String name);

    // List<Government> findGovernmentByLanguageShortNameOrderByNameAsc(String languageShortName);
}