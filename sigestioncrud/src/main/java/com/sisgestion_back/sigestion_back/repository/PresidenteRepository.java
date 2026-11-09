package com.sisgestion_back.sigestion_back.repository;

import com.sisgestion_back.sigestion_back.model.entity.Presidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresidenteRepository extends JpaRepository<Presidente,Long> {

}
