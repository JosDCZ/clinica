package com.ues.edu.clinica.service;

import com.ues.edu.clinica.model.Consulta;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IConsultaService extends ICRUD<Consulta>{

    void generarReportePorConsulta(HttpServletResponse response) throws IOException;

    //void generarReportePorConsulta(HttpServletResponse response, int idEspecialidadParam, String fecha) throws IOException;

    void generarReportePorConsultaGraficoBarras(HttpServletResponse response) throws IOException;

    void generarReportePorCantidadMedicosPorEspGraficoPastel(HttpServletResponse response) throws IOException;

    void generarReportePorCantidadPacientesPorEspGraficoLine(HttpServletResponse response) throws IOException;

    void generarReportePorConsultaParam(HttpServletResponse response, int idEspecialidadParam, String fechaConsultaParam) throws IOException;
}
