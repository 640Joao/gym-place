package com.buscagym.api.repository;

import com.buscagym.api.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Long> {

    @Query("SELECT DISTINCT a FROM Academia a LEFT JOIN a.planos p WHERE " +
           "(:termo IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
           "OR LOWER(a.cidade) LIKE LOWER(CONCAT('%', :termo, '%')) " +
           "OR LOWER(a.bairro) LIKE LOWER(CONCAT('%', :termo, '%'))) AND " +
           "(:precoMax IS NULL OR p.precoMensal <= :precoMax)")
    List<Academia> buscarPorFiltros(@Param("termo") String termo, @Param("precoMax") Double precoMax);
}