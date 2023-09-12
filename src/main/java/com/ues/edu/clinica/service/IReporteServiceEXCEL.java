package com.ues.edu.clinica.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IReporteServiceEXCEL {
    void generateExcel(HttpServletResponse response) throws IOException;
}
