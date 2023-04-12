package com.csaba79coder.bestprotocol.model.government.persistence;

import com.csaba79coder.bestprotocol.model.government.entity.PreviousJobTitleTranslation;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviousJobTitleTranslationRepository extends JpaRepository<PreviousJobTitleTranslation, Long> {

    List<PreviousJobTitleTranslation> findByRepresentativeAndLanguageShortName(Representative representative, String languageShortName);
}
