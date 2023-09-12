package com.ues.edu.clinica.repository;

import com.ues.edu.clinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMedicoRepo extends JpaRepository<Medico,Integer> {
    @Query("from Medico m where (LOWER(m.nombreMedico) like %:filtro% or LOWER(m.apellidoMedico) like %:filtro%)")
    List<Medico> buscarMedico(@Param("filtro") String filtro);
}
