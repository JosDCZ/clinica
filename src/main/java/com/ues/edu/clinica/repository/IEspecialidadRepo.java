package com.ues.edu.clinica.repository;

import com.ues.edu.clinica.model.Especialidad;
import com.ues.edu.clinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEspecialidadRepo extends JpaRepository<Especialidad,Integer> {
    @Query("from Especialidad es where (LOWER(es.nombreEspeciadad) like %:filtro% )")
    List<Especialidad> buscarEspecialidad(@Param("filtro") String filtro);
}
