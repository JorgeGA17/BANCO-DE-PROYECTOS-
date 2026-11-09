package com.sisgestion_back.sigestion_back.repository;

import com.sisgestion_back.sigestion_back.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByXcorreoInstitucional (String xcorreoInstitucional);

    Optional<User> findByXcorreoInstitucional(String xcorreoInstitucional);

    Optional<User> findByResetPasswordToken(String resetPasswordToken);

}
