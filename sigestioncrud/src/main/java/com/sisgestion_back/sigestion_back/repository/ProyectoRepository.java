package com.sisgestion_back.sigestion_back.repository;

import com.sisgestion_back.sigestion_back.model.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto,Long> {

    @Query("SELECT p FROM Proyecto p WHERE " +
            "LOWER(p.xnombreProyecto) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.xresumen) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Proyecto> searchByKeyword(@Param("keyword") String keyword);

}
