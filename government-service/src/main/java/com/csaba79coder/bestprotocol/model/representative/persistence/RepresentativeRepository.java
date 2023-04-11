package com.csaba79coder.bestprotocol.model.representative.persistence;

import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, UUID> {
    // List<RepresentativeAdminModel> findRepresentativeByGovernmentId(Long id);
    // Page<RepresentativeAdminModel> findByGovernmentId(@Param("government_id") Long governmentId, Pageable pageable);
    List<Representative> findRepresentativeByLanguageShortNameAndGovernmentId(@Param("language_short_name") String languageShortname, @Param("government_id") Long governmentId);
    List<Representative> findAllByLanguageShortName(String languageShortName);
    Representative findRepresentativeByLanguageShortName(String languageShortName);

    List<Representative> findRepresentativeByGovernmentId( @Param("government_id") Long governmentId);
}
