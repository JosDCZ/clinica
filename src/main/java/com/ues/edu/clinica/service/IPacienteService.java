package com.ues.edu.clinica.service;

import com.ues.edu.clinica.model.Paciente;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IPacienteService extends ICRUD<Paciente>{
    List<Paciente> buscarPaciente(String filtro);

    //este metodo se utiliza para reportes unicamente
    void generarReportePacientes(HttpServletResponse response) throws IOException;
}
