package com.csaba79coder.bestprotocol.model.government.persistence;

import com.csaba79coder.bestprotocol.model.government.entity.PreviousJobTitleTranslation;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**

 Repository interface for accessing and manipulating {@link PreviousJobTitleTranslation} entities.
 */
@Repository
public interface PreviousJobTitleTranslationRepository extends JpaRepository<PreviousJobTitleTranslation, Long> {

    /**

     Finds a list of {@link PreviousJobTitleTranslation} entities that belong to the specified representative and match the specified language short name.
     @param representative the representative entity to which the previous job title translations belong
     @param languageShortName the language short name of the previous job title translations to be retrieved
     @return a list of {@link PreviousJobTitleTranslation} entities that belong to the specified representative and match the specified language short name
     */
    List<PreviousJobTitleTranslation> findByRepresentativeAndLanguageShortName(Representative representative, String languageShortName);
}
