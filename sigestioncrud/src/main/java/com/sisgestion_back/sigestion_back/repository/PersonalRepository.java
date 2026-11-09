package com.sisgestion_back.sigestion_back.repository;

import com.sisgestion_back.sigestion_back.model.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {

    boolean existsByXnombreAndXapellido(String xnombre, String xapellido);

    boolean existsByXnombreAndXapellidoAndUser_UserPkNot(String xnombre, String xapellido, Long userPk);
}
