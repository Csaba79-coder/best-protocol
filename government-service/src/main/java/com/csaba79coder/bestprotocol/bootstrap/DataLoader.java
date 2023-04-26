package com.csaba79coder.bestprotocol.bootstrap;

import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeRepository;
import com.csaba79coder.bestprotocol.model.representative.persistence.RepresentativeTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

// @Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RepresentativeRepository representativeRepository;
    private final RepresentativeTranslationRepository representativeTranslationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Representative representativeSaved = representativeRepository.findById(UUID.fromString("04e55934-397a-4f61-8d74-d1ac4a89a280")).get();

        Representative representative = new Representative();
        representative.setEmail("finance-assistant@finance.org");
        representative.setPhoneNumber("+36-...");
        representative.setImage(representativeSaved.getImage());
        representativeRepository.save(representative);*/

        /*RepresentativeTranslation translation = new RepresentativeTranslation();
        Optional<Representative> representative = representativeRepository.findById(UUID.fromString("04e55934-397a-4f61-8d74-d1ac4a89a280"));
        if (representative.isPresent()) {
            Representative currentRepr = representative.get();
            translation.setRepresentative(currentRepr);
            translation.setLanguageShortName("hu");
            representativeTranslationRepository.save(translation);
        }*/
    }
}
