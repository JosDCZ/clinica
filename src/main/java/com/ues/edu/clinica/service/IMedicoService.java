package com.ues.edu.clinica.service;

import com.ues.edu.clinica.model.Medico;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IMedicoService extends ICRUD<Medico>{
    List<Medico> buscarMedico(String filtro);

    void generarReporteMedicos(HttpServletResponse response) throws IOException;
}
