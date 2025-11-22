package com.agenciaviagens.repository;

import com.agenciaviagens.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, String> {
    
    // Método customizado com JPQL pra pesquisar
    @Query("SELECT d FROM Destino d WHERE " +
           "LOWER(d.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(d.localizacao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Destino> pesquisar(@Param("termo") String termo);
    
    // O Spring Data JPA cria isso automaticamente pelo nome do método!
    List<Destino> findByLocalizacaoContainingIgnoreCase(String localizacao);
    
    // Busca destinos com nota acima de um valor
    List<Destino> findByNotaMediaGreaterThanEqual(double notaMedia);
}