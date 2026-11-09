package com.sisgestion_back.sigestion_back.repository;

import com.sisgestion_back.sigestion_back.Enum.ERole;
import com.sisgestion_back.sigestion_back.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //Buscar Rol x nombre
    Optional<Role> findByName(ERole name);

}
