package com.ues.edu.clinica.repository;

import com.ues.edu.clinica.dtos.ICantidadMedicosEspReporteDTO;
import com.ues.edu.clinica.dtos.ICantidadPacientesEspReporteDTO;
import com.ues.edu.clinica.dtos.IConsultasMedicasDTOReporte;
import com.ues.edu.clinica.dtos.ITotalConsultasMedicasPorEspecReporteDTO;
import com.ues.edu.clinica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultaRepo extends JpaRepository<Consulta,
        Integer> {
    @Query(value = " SELECT consulta.fecha_consulta as fechaConsulta, consulta.hora_consulta as horaConsulta, consulta.num_consultorio as numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad, concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as nombreCompletoMedico, concat_ws(' ', paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente FROM consulta INNER JOIN especialidad ON consulta.id_especialidad = especialidad.id_especialidad INNER JOIN medico ON consulta.id_medico = medico.id_medico INNER JOIN paciente ON consulta.id_paciente = paciente.id_paciente ORDER BY especialidad.nombre_especiadad", nativeQuery = true)
    List<IConsultasMedicasDTOReporte>
    totalConsultasPacientes();

    @Query(value = " SELECT e.nombre_especiadad as nombreEspecialidad, count(id_consulta) as cantidadConsultaPorEspecialidad FROM especialidad e INNER JOIN consulta c ON e.id_especialidad=c.id_especialidad GROUP BY e.id_especialidad ORDER BY e.id_especialidad DESC", nativeQuery = true) List<ITotalConsultasMedicasPorEspecReporteDTO> cantidadConsultaPorEspecialidadGrafBarras();

    @Query(value = "SELECT e.nombre_especiadad as nombreEspecialidad,\n" +
            "count(c.id_medico) as cantidaMedicos\n" +
            "FROM especialidad e \n" +
            "INNER JOIN consulta c\n" +
            "ON e.id_especialidad=c.id_especialidad\n" +
            "INNER JOIN medico m\n" +
            "ON m.id_medico = c.id_medico\n" +
            "GROUP BY e.id_especialidad\n" +
            "ORDER BY e.id_especialidad DESC", nativeQuery = true) List<ICantidadMedicosEspReporteDTO> cantidadMedicosPorEspecialidadGrafPastel();

    @Query(value = "SELECT e.nombre_especiadad as nombreEspecialidad,\n" +
            "count(DISTINCT c.id_paciente) as cantidadpacientes\n" +
            "FROM especialidad e \n" +
            "INNER JOIN consulta c\n" +
            "ON e.id_especialidad=c.id_especialidad\n" +
            "INNER JOIN paciente p\n" +
            "ON p.id_paciente = c.id_paciente\n" +
            "GROUP BY e.id_especialidad\n" +
            "ORDER BY e.id_especialidad DESC", nativeQuery = true) List<ICantidadPacientesEspReporteDTO> cantidadPacientesPorEspecialidadGrafLine();

    @Query(value = "SELECT especialidad.id_especialidad as codigoEspecialidad, consulta.fecha_consulta as \n" +
            "fechaConsulta, consulta.hora_consulta as horaConsulta, consulta.num_consultorio as \n" +
            "numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad, concat_ws(' ', \n" +
            "medico.nombre_medico, medico.apellido_medico) as nombreCompletoMedico, concat_ws(' ', \n" +
            "paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente FROM \n" +
            "consulta INNER JOIN especialidad ON consulta.id_especialidad = especialidad.id_especialidad INNER \n" +
            "JOIN medico ON consulta.id_medico = medico.id_medico INNER JOIN paciente ON \n" +
            "consulta.id_paciente = paciente.id_paciente HAVING \n" +
            "especialidad.id_especialidad=:codigoEspecialidadParam AND \n" +
            "consulta.fecha_consulta=:fechaConsultaParam", nativeQuery = true) List<IConsultasMedicasDTOReporte> cantidadConsultasPacientes(int codigoEspecialidadParam, String fechaConsultaParam);
}