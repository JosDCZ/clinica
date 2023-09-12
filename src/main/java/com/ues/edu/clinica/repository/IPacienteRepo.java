package com.ues.edu.clinica.repository;

import com.ues.edu.clinica.model.Medico;
import com.ues.edu.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepo extends JpaRepository<Paciente,Integer> {
    @Query("from Paciente p where (LOWER(p.nombrePaciente) like %:filtro% or LOWER(p.apellidoPaciente) like %:filtro%)")
    List<Paciente> buscarPaciente(@Param("filtro") String filtro);
}
