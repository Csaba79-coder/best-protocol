package com.csaba79coder.bestprotocol.model.representative.persistence;

import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**

 This repository interface provides access to the "representative" table in the database. It extends the Spring Data JPA

 {@link JpaRepository} interface and provides additional methods to query the Representative entities by government ID.

 The generic type parameters of {@link JpaRepository} are set to {@link Representative} and {@link UUID} to indicate

 the entity type and ID type respectively.
 */
@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, UUID> {

    List<Representative> findRepresentativeByGovernmentId( @Param("government_id") Long governmentId);
}
