package com.csaba79coder.bestprotocol.model.representative.persistence;

import com.csaba79coder.bestprotocol.model.representative.entity.Government;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentRepository extends JpaRepository<Government, Long> {
}
